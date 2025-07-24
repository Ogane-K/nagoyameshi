package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.Plan;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.entity.UserRoleId;
import com.example.nagoyameshi.exception.RoleNotFoundException;
import com.example.nagoyameshi.exception.UserNotFoundException;
import com.example.nagoyameshi.mapper.StripeMapper;
import com.example.nagoyameshi.repository.PaymentRepository;
import com.example.nagoyameshi.repository.PlanRepository;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.UserRoleRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoicePayment;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.SetupIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionItem;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.SetupIntentCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Service
public class StripeService {

        private final UserRepository userRepository;
        private final PlanRepository planRepository;
        private final SubscriptionRepository subscriptionRepository;
        private final PaymentRepository paymentRepository;
        private final RoleRepository roleRepository;
        private final UserRoleRepository userRoleRepository;

        public StripeService(UserService userService,
                        UserRepository userRepository,
                        PlanRepository planRepository,
                        SubscriptionRepository subscriptionRepository,
                        PaymentRepository paymentRepository,
                        RoleRepository roleRepository,
                        UserRoleRepository userRoleRepository) {

                this.userRepository = userRepository;
                this.planRepository = planRepository;
                this.subscriptionRepository = subscriptionRepository;
                this.paymentRepository = paymentRepository;
                this.roleRepository = roleRepository;
                this.userRoleRepository = userRoleRepository;

        }

        // 共用フィールド
        private String successUrl;
        private String canceledUrl;
        private String premiumProductId;
        private String superPremiumProductId;

        // 各共用変数やフィールドに値をセット
        @PostConstruct
        private void init() {
                // Stripeのシークレットキーを設定する
                Dotenv dotenv = Dotenv.configure().load();
                String stripeKey = dotenv.get("STRIPE_SECRET_KEY");
                Stripe.apiKey = stripeKey;

                successUrl = dotenv.get("STRIPE_SUCCESS_URL");
                canceledUrl = dotenv.get("STRIPE_CANCELED_URL");
                premiumProductId = dotenv.get("STRIPE_PRODUCT_300YEN");
                superPremiumProductId = dotenv.get("STRIPE_PRODUCT_500YEN");

        }

        // 認証中のユーザーを取得
        public User getCurrentUser() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
                        return userDetailsImpl.getUser();
                }
                throw new IllegalStateException("ログイン情報が取得できませんでした");
        }

        // ★顧客オブジェクトを作成し、カスタマーIdをDBに保存
        @Transactional
        public Customer createCustomer() throws StripeException {
                User user = getCurrentUser();

                // すでにカスタマーidがあるなら再利用する
                if (user.getStripeCustomerId() != null) {
                        return Customer.retrieve(user.getStripeCustomerId());
                }

                CustomerCreateParams params = CustomerCreateParams.builder()
                                .setName(user.getName())
                                .setEmail(user.getEmail())
                                .setMetadata(Map.of("app_user_id", user.getId().toString()))
                                .build();

                Customer customer = Customer.create(params);

                // ユーザーにCustomerIDを保存する
                user.setStripeCustomerId(customer.getId());
                userRepository.save(user);

                return customer;
        }

        // ★カードの登録 ★SetupIntentの作成
        @Transactional
        public String createSetupIntent(String customerId) throws StripeException {
                SetupIntentCreateParams params = SetupIntentCreateParams.builder()
                                .setCustomer(customerId)
                                .addPaymentMethodType("card")
                                .build();

                SetupIntent setupIntent = SetupIntent.create(params);
                return setupIntent.getClientSecret();
        }

        @Transactional
        // ★カード情報（PaymentMethod)を顧客に紐づける
        public void attachPaymentMethodToCustomer(String paymentMethodId, String customerId) throws StripeException {
                PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
                PaymentMethodAttachParams attachParams = PaymentMethodAttachParams.builder()
                                .setCustomer(customerId)
                                .build();

                paymentMethod.attach(attachParams);
        }

        @Transactional
        // ★指定した顧客idの規定の支払い方法を、指定した支払い方法に変更する
        public void setDefaultPaymentMethod(String customerId, String paymentMethodId) throws StripeException {
                Customer customer = Customer.retrieve(customerId);

                CustomerUpdateParams updateParams = CustomerUpdateParams.builder()
                                .setInvoiceSettings(
                                                CustomerUpdateParams.InvoiceSettings.builder()
                                                                .setDefaultPaymentMethod(paymentMethodId)
                                                                .build())
                                .build();

                customer.update(updateParams);
        }

        @Transactional
        // ★契約情報を作成
        public Subscription createSubscription(String customerId,
                        String priceId,
                        String PaymentMethod) throws StripeException {

                SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                                .setCustomer(customerId)
                                .addItem(
                                                SubscriptionCreateParams.Item.builder()
                                                                .setPrice(priceId)
                                                                .build())
                                .setDefaultPaymentMethod(PaymentMethod)
                                .build();

                return Subscription.create(params);
        }

        @Transactional
        // 契約のセッションを作成
        public Session createCheckoutSession(String customerId, String priceId) throws StripeException {
                SessionCreateParams params = SessionCreateParams.builder()
                                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                                .setCustomer(customerId)
                                .setSuccessUrl(successUrl)
                                .setCancelUrl(canceledUrl)
                                .addLineItem(
                                                SessionCreateParams.LineItem.builder()
                                                                .setPrice(priceId)
                                                                .setQuantity(1L)
                                                                .build())
                                .build();

                System.out.println("params mode :" + params.getMode());
                System.out.println("params Customer :" + params.getCustomer());
                System.out.println("params SucUrl :" + params.getSuccessUrl());
                System.out.println("params CancelUrl :" + params.getCancelUrl());

                return Session.create(params);
        }

        // ★契約プランのidを取得
        public String getPriceIdFromPlan(Integer planId) {
                Plan plan = planRepository.findById(planId)
                                .orElseThrow(() -> new IllegalArgumentException("指定されたプランが存在しません。"));

                return plan.getPriceId();
        }

        // ★課金プラン成立後のDB登録
        @Transactional
        public String handleCheckoutSessionCompleted(Event event) throws StripeException {

                // StripeAPIのバージョンと、javaライブラリのバージョンを表示する
                displayOfStripeVersions(event);

                // イベントデシリアライザーを作成する
                EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

                // 1.セッションの取得
                Session session = eventToSession(event, deserializer);

                // 2.セッションを元に、ユーザーの特定
                User user = sessionToUserEntity(session);

                // 3.Stripeからセッションを元にSubscriptionを取得
                Subscription subscription = sessionToSubscription(session);

                // 4.課金されたプランの特定
                Plan plan = findPlanFromSubscription(subscription);

                // 5.Paymentエンティティの作成
                Payment payment = new Payment();

                // 6.SessionからpaymentMethodを取得 取得できない場合ならnull
                PaymentMethod paymentMethod = null;
                try {
                        paymentMethod = getPaymentMethodFromSubscription(subscription);
                } catch (Exception e) {
                        System.err.println("paymentMethodの取得でエラーが発生しました。 : " + e.getMessage());
                }

                // 7.各情報を、Paymentエンティティにマッピングする
                StripeMapper.mapToPaymentEntity(payment, user, paymentMethod);

                // 8.paymentエンティティをDBに保存する
                paymentRepository.save(payment);

                // 9.Subscriptionエンティティ（DB保存用）を作成
                com.example.nagoyameshi.entity.Subscription userSub = new com.example.nagoyameshi.entity.Subscription();

                // 10.Subscriptionエンティティに各情報をマッピングする
                StripeMapper.mapToSubscriptionEntity(userSub, subscription, user, plan, payment);

                // 11.SubscriptionエンティティをDBに保存する
                subscriptionRepository.save(userSub);

                // ★ロールの更新処理★

                // 12.購入された有料プランに合わせたロール情報を取得する
                Role roleData = findRoleFromSubscription(subscription);

                // 13.すでに有料会員のロールを持っていないかチェックして、チェックが通ったら更新
                assignRoleIfAbsent(roleData, user);

                return roleData.getName();
        }

        @Transactional
        // ★サブスクリプションをキャンセルする
        public String cancelSubscription(User user) throws StripeException {

                // 対象のサブスクリプションを取得
                com.example.nagoyameshi.entity.Subscription userSub = getSubscriptionIdFromUser(user);

                String subId = userSub.getStripeSubscriptionId();

                // Stripe上でキャンセル実行
                Subscription subscription = Subscription.retrieve(subId);
                subscription.cancel();

                // DBの契約レコードのステータスをキャンセル済みに更新
                markSubscriptionAsCanceled(userSub);

                // 対象ユーザーのロールを変更する
                String newRoleName = changeUserRoleToFreeMember(user);

                return newRoleName;
        }

        // ★支払い情報を更新する
        public Payment updatePayment(User user) throws StripeException {

                // カスタマーを取得する

                if (user.getStripeCustomerId() == null) {
                        return new Payment();
                }

                Customer customer = Customer.retrieve(user.getStripeCustomerId());

                String paymentMethodId = customer.getInvoiceSettings().getDefaultPaymentMethod();

                PaymentMethod paymentMethod = null;
                if (paymentMethodId != null) {
                        paymentMethod = PaymentMethod.retrieve(paymentMethodId);
                }

                Payment payment = paymentRepository.findTopByUserOrderByIdDesc(user)
                                .orElse(new Payment());

                StripeMapper.mapToPaymentEntity(payment, user, paymentMethod);

                paymentRepository.save(payment);

                return payment;
        }

        // セッションからユーザーを特定する
        public User sessionToUserEntity(Session session) throws StripeException {

                // セッションからCustomerIDを取得
                String customerId = session.getCustomer();

                if (customerId == null) {
                        throw new IllegalStateException("Customerが取得できませんでした。");
                }

                // StripeAPI経由でCustomerを取得
                Customer customer = Customer.retrieve(customerId);

                // Customer中のメタデータからユーザーidを取得

                String userIdStr = customer.getMetadata().get("app_user_id");

                if (userIdStr == null || !userIdStr.matches("\\d+")) {
                        throw new IllegalArgumentException("app_user_idが不正な値です: " + userIdStr);
                }
                Integer userId = Integer.parseInt(userIdStr);

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("ユーザーが見つかりません　ユーザーid : " + userId));

                return user;
        }

        // イベントからセッション情報を取得
        public Session eventToSession(Event event,
                        EventDataObjectDeserializer deserializer) throws StripeException {

                Session session = null;

                if (deserializer.getObject().isPresent()) {
                        StripeObject stripeObject = deserializer.getObject().get();
                        if (stripeObject instanceof Session) {
                                session = (Session) stripeObject;
                        } else {
                                throw new IllegalStateException("イベントはセッションの形にできませんでした");
                        }
                } else {
                        throw new IllegalStateException("デシリアライザーの値が空です。");
                }

                // 部分的なsessionから完全なSessionを取得する
                Session fullSession = Session.retrieve(session.getId());

                return fullSession;
        }

        // セッションからサブスクリプションへ変換
        public Subscription sessionToSubscription(Session session) throws StripeException {
                String subscriptionId = session.getSubscription();

                Subscription subscription = null;
                if (subscriptionId != null) {
                        subscription = Subscription.retrieve(subscriptionId);
                } else {
                        throw new IllegalStateException(("セッションからサブスクリプションへの変換に失敗しました。"));
                }
                return subscription;
        }

        // 契約情報(サブスクリプション)で購入された課金プランを特定する
        public Plan findPlanFromSubscription(Subscription subscription) {

                List<SubscriptionItem> items = subscription.getItems().getData();
                if (items == null || items.isEmpty()) {
                        throw new IllegalStateException("サブスクリプションにアイテムが存在しません。");
                }

                String priceId = items.get(0).getPrice().getId();
                if (priceId == null) {
                        throw new IllegalStateException("課金プランの取得に失敗しました。");
                }

                return planRepository.findByPriceId(priceId)
                                .orElseThrow(() -> new IllegalStateException("該当する課金プランが見つかりません。"));
        }

        // Subscriptionオブジェクトを元に、PaymentMethodを取得する
        public PaymentMethod getPaymentMethodFromSubscription(Subscription subscription) throws StripeException {

                Invoice invoice = subscription.getLatestInvoiceObject();

                PaymentIntent paymentIntent = null;

                // paymentntentを取得する
                List<InvoicePayment> payments = invoice.getPayments().getData();
                if (!payments.isEmpty()) {
                        InvoicePayment latestPayment = payments.get(payments.size() - 1);

                        InvoicePayment.Payment paymentMeta = latestPayment.getPayment();

                        String paymentIntentId = paymentMeta.getPaymentIntent();
                        paymentIntent = PaymentIntent.retrieve(paymentIntentId);

                }

                // PaymentMethodのIDを取得する
                String paymentMethodId = null;
                if (paymentIntent != null) {
                        paymentMethodId = paymentIntent.getPaymentMethod();
                } else {
                        throw new IllegalStateException("PaymentIntentの取得に失敗しました。");
                }

                if (paymentMethodId == null || paymentMethodId.isEmpty()) {
                        throw new IllegalStateException("paymentMethodIdの取得に失敗しました。");
                }

                PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

                return paymentMethod;
        }

        // 有料プランの種類に合わせて変更するロールを取得する
        public Role findRoleFromSubscription(Subscription subscription) {

                // 契約情報から会員プランの価格IDを取得する
                String priceId = subscription.getItems().getData().get(0).getPrice().getId();

                Role role;
                if (priceId.equals(premiumProductId)) { // ← プレミアム会員用 Price ID
                        role = roleRepository.findById(3)
                                        .orElseThrow(() -> new RoleNotFoundException("有料会員ロールが見つかりません。"));

                } else if (priceId.equals(superPremiumProductId)) {
                        role = roleRepository.findById(4)
                                        .orElseThrow(() -> new RoleNotFoundException("スーパー有料会員ロールが見つかりません。"));
                } else {
                        throw new IllegalStateException("未対応の価格ID: " + priceId);
                }
                return role;
        }

        // 指定したユーザーが、すでにそのロールを持っているかのチェック持っているならtrue
        public boolean alreadyHasRole(Role role, User user) {
                return userRoleRepository.findByUser(user).stream()
                                .anyMatch(userRole -> userRole.getRole().getId() == role.getId());
        }

        // 指定したユーザーがロールを持っていなければ、そのロールを付与する
        public void assignRoleIfAbsent(Role role, User user) {

                if (!alreadyHasRole(role, user)) {
                        UserRoleId id = new UserRoleId();
                        id.setUserId(user.getId());
                        id.setRoleId(role.getId());

                        UserRole userRole = new UserRole();
                        userRole.setId(id);
                        userRole.setRole(role);
                        userRole.setUser(user);

                        userRoleRepository.deleteByUser(user);
                        userRoleRepository.save(userRole);
                } else {
                        throw new IllegalStateException("該当のユーザーはすでに " + role.getName() + " のロールを所持しています。");
                }
        }

        // Stripe関連のバージョンを確認する
        public void displayOfStripeVersions(Event event) {
                System.out.println("Stripe API Version: " + event.getApiVersion());
                System.out.println("stripe-java Version: " + Stripe.VERSION + ", stripe-java API Version: "
                                + Stripe.API_VERSION);
        }

        // 指定したユーザーの一番新しいサブスクリプションを取得する
        public com.example.nagoyameshi.entity.Subscription getSubscriptionIdFromUser(User user) {

                com.example.nagoyameshi.entity.Subscription subscription = subscriptionRepository
                                .findFirstByUserOrderByStartDateDesc(user)
                                .orElseThrow(() -> new IllegalStateException("サブスクリプションが見つかりません"));

                // 契約の有効チェック
                if (subscription.isCanceled()) {
                        throw new IllegalStateException("このユーザーに有効な課金プランはありません。");
                }

                return subscription;
        }

        // 対象のサブスクリプションをDB上でキャンセル状態にする
        public void markSubscriptionAsCanceled(com.example.nagoyameshi.entity.Subscription userSub) {
                userSub.setCanceled(true);
                userSub.setCanceledAt(LocalDateTime.now());
                subscriptionRepository.save(userSub);
        }

        // 指定したユーザーのロールを、無料会員に変更する
        public String changeUserRoleToFreeMember(User user) {

                UserRole userRole = new UserRole();
                UserRoleId userRoleId = new UserRoleId();

                Role role = roleRepository.findByName("ROLE_FREE_MEMBER")
                                .orElseThrow(() -> new RoleNotFoundException("無料会員のロールが見つかりません。"));

                userRoleId.setRoleId(role.getId());
                userRoleId.setUserId(user.getId());

                userRole.setId(userRoleId);
                userRole.setRole(role);
                userRole.setUser(user);

                userRoleRepository.deleteByUser(user);
                userRoleRepository.save(userRole);

                String newRoleName = role.getName();

                return newRoleName;
        }

}

package com.example.nagoyameshi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.DayType;
import com.example.nagoyameshi.entity.Holiday;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.exception.DuplicateRestaurantNameException;
import com.example.nagoyameshi.exception.ImageStorageException;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.HolidayService;
import com.example.nagoyameshi.service.RestaurantCategoryService;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
public class AdminRestaurantController {

    // DIするクラスたち
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final RestaurantCategoryService restaurantCategoryService;
    private final HolidayService holidayService;

    // コンストラクタインジェクション
    public AdminRestaurantController(RestaurantService restaurantService,
            RestaurantCategoryService restaurantCategoryService,
            CategoryService categoryService,
            CategoryRepository categoryRepository,
            HolidayService holidayService) {
        this.restaurantService = restaurantService;
        this.restaurantCategoryService = restaurantCategoryService;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.holidayService = holidayService;
    }

    // 管理者用店舗一覧画面
    @GetMapping("/admin/restaurants")
    public String index(@PageableDefault(page = 0, size = 100, direction = Direction.ASC) Pageable pageable,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Page<Restaurant> restaurantsPage;

        // キーワードが存在したら、キーワード検索する ソレ以外は全店舗を取得
        if (keyword == null || keyword.isEmpty()) {
            restaurantsPage = restaurantService.findallRestaurants(pageable);
        } else {
            restaurantsPage = restaurantService.findRestaurantsByNameLike(keyword, pageable);
        }

        model.addAttribute("restaurantPage", restaurantsPage);
        model.addAttribute("keyword", keyword);

        return "admin/restaurants/index";
    }

    // 管理者用店舗詳細情報画面
    @GetMapping("/admin/restaurants/{id}")
    public String show(@PathVariable(value = "id") Integer id,
            RedirectAttributes redirectAttributes, Model model) {

        // 店舗のデーター
        Restaurant restaurant = new Restaurant();
        // 店舗が所属しているカテゴリーのリスト
        List<Category> categoryList = new ArrayList<>();
        // 店舗に設定している休日情報のリスト
        List<Holiday> holidayList = new ArrayList<>();

        // ビューに送る店舗エンティティを用意
        try {
            restaurant = restaurantService.findRestaurantById(id);
            categoryList = restaurantCategoryService.findCategoriesByRestaurant(restaurant);
            holidayList = holidayService.findHolidaysByRestaurant(restaurant);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            System.err.println(e.getMessage());

            return "redirect:/admin/restaurants";
        }

        model.addAttribute("holidays", holidayList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("restaurant", restaurant);

        return "admin/restaurants/show";
    }

    // 店舗の新規作成画面
    @GetMapping("/admin/restaurants/register")
    public String register(@ModelAttribute RestaurantRegisterForm restaurantRegisterForm,
            Model model) {

        model.addAttribute("regularHolidays", DayType.values());
        model.addAttribute("categoryList", categoryService.findAllCategoriesList());
        return "admin/restaurants/register";
    }

    // 店舗の新規作成リクエスト
    @PostMapping("/admin/restaurants/create")
    public String create(@ModelAttribute @Validated RestaurantRegisterForm registerForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // エラーフラグ
        boolean hasErrors = false;
        // カテゴリーIdの入れ物
        List<Integer> categoryIds = new ArrayList<>();
        // 店舗に設定している休日情報のリスト
        List<String> holidayCodes = new ArrayList<>();

        // ◆各条件に一つでも合致したら、エラーフラグをONにする◆

        // バリエーションエラーが存在するか
        if (bindingResult.hasErrors()) {
            System.out.println("バリデーションエラー：" + bindingResult.getAllErrors());
            hasErrors = true;
        }

        // カテゴリーIdの重複チェック 成功ならリストにいれる。 失敗ならエラーフラグOn
        if (!registerForm.hasDuplicateCategory()) {
            categoryIds = registerForm.getCategoryIds();
        } else {
            bindingResult.rejectValue("categoryId", "category.invalid", "複数の同じカテゴリーは適用できません。");
            hasErrors = true;
        }

        try {
            // 最低価格が最高価格を超えていないか
            if (!restaurantService.isValidPrices(registerForm.getLowestPrice(), registerForm.getHighestPrice())) {
                bindingResult.rejectValue("highestPrice", "price.range.invalid", "最高価格は最低価格以上にしてください。");
                hasErrors = true;
            }

            // 閉店時間が開店時間より前になっていないか
            if (!restaurantService.isValidBusinessHours(registerForm.getOpeningTime(),
                    registerForm.getClosingTime())) {
                bindingResult.rejectValue("closingTime", "business.hours.invalid", "閉店時間は開店時間より後にしてください。");
                hasErrors = true;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("不正な値が入力されました。: " + e.getMessage());
            hasErrors = true;
        }

        // エラーフラグがONになっている場合には、情報入力画面にもどる
        if (hasErrors) {

            model.addAttribute("categoryList", categoryService.findAllCategoriesList());
            model.addAttribute("regularHolidays", DayType.values());
            return "admin/restaurants/register";
        }

        // エラーチェックを終えたら、Restaurantエンティティを作成して
        // フォームクラスの内容をつめこむ
        Restaurant restaurant = new Restaurant();

        restaurant.setName(registerForm.getName());
        restaurant.setDescription(registerForm.getDescription());
        restaurant.setPostalCode(registerForm.getPostalCode());
        restaurant.setAddress(registerForm.getAddress());
        restaurant.setLatitude(registerForm.getLatitude());
        restaurant.setLongitude(registerForm.getLongitude());
        restaurant.setOpeningTime(registerForm.getOpeningTime());
        restaurant.setClosingTime(registerForm.getClosingTime());
        restaurant.setHighestPrice(registerForm.getHighestPrice());
        restaurant.setLowestPrice(registerForm.getLowestPrice());
        restaurant.setSeatingCapacity(registerForm.getSeatingCapacity());

        // 休日情報を、専用のフィールドに格納
        holidayCodes = registerForm.getHolidayCodes();

        // 店舗情報の登録
        try {
            restaurantService.createRestaurant(restaurant, registerForm.getImageFile(), categoryIds, holidayCodes);
        } catch (DuplicateRestaurantNameException e) {
            System.err.println(e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/restaurants";
        } catch (IOException e) {
            System.err.println(new ImageStorageException("画像の保存に失敗しました。", e));
            bindingResult.reject("general.error", "予期せぬエラーが発生しました。もう一度お試しください。");

            model.addAttribute("categoryList", categoryService.findAllCategoriesList());
            model.addAttribute("regularHolidays", DayType.values());
            return "admin/restaurants/register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "店舗情報の登録が完了しました。");
        return "redirect:/admin/restaurants";
    }

    // 店舗情報の編集画面
    @GetMapping("/admin/restaurants/{id}/edit")
    public String getMethodName(@PathVariable(value = "id") Integer id,
            @ModelAttribute RestaurantEditForm restaurantEditForm,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 店舗情報の雛形を用意
        Restaurant restaurant = new Restaurant();
        // 店舗と紐づいているカテゴリーのリスト
        List<Category> selectedCategoryList = new ArrayList<>();
        // 店舗に設定している休日情報のリスト
        List<String> holidayCodes = new ArrayList<>();

        // カテゴリーの一覧マスタ
        List<Category> categoryList = categoryService.findAllCategoriesList();

        // 指定したIDの店舗情報を取得
        try {
            restaurant = restaurantService.findRestaurantById(id);
            selectedCategoryList = restaurantCategoryService.findCategoriesByRestaurant(restaurant);
            holidayCodes = holidayService.extractHolidayCodes(restaurant);
        } catch (RestaurantNotFoundException e) {

            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/admin/restaurants";
        }

        // 画像ファイルの名前を取得
        String currentImageFileName = restaurant.getImage();

        // フォームクラスに店舗の情報をそれぞれセット

        restaurantEditForm.setName(restaurant.getName());
        restaurantEditForm.setCurrentImageFileName(currentImageFileName);
        restaurantEditForm.setDescription(restaurant.getDescription());
        restaurantEditForm.setPostalCode(restaurant.getPostalCode());
        restaurantEditForm.setAddress(restaurant.getAddress());
        restaurantEditForm.setLatitude(restaurant.getLatitude());
        restaurantEditForm.setLongitude(restaurant.getLongitude());
        restaurantEditForm.setOpeningTime(restaurant.getOpeningTime());
        restaurantEditForm.setClosingTime(restaurant.getClosingTime());
        restaurantEditForm.setHighestPrice(restaurant.getHighestPrice());
        restaurantEditForm.setLowestPrice(restaurant.getLowestPrice());
        restaurantEditForm.setSeatingCapacity(restaurant.getSeatingCapacity());

        // 休日情報をフォームクラスにセット
        restaurantEditForm.setHolidayCodes(holidayCodes);

        // カテゴリー1~3をフォームクラスにセット
        if (selectedCategoryList.size() > 0)
            restaurantEditForm.setCategoryId1(selectedCategoryList.get(0).getId());
        if (selectedCategoryList.size() > 1)
            restaurantEditForm.setCategoryId2(selectedCategoryList.get(1).getId());
        if (selectedCategoryList.size() > 2)
            restaurantEditForm.setCategoryId3(selectedCategoryList.get(2).getId());

        model.addAttribute("regularHolidays", DayType.values());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("restaurantEditForm", restaurantEditForm);
        model.addAttribute("restaurant", restaurant);
        return "admin/restaurants/edit";
    }

    // 店舗情報の編集
    @PostMapping("/admin/restaurants/{id}/update")
    public String update(@PathVariable(value = "id") Integer id,
            @ModelAttribute @Validated RestaurantEditForm editForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // エラーフラグ
        boolean hasErrors = false;

        // カテゴリーIdの入れ物
        List<Integer> categoryIds = new ArrayList<>();

        // カテゴリーのマスタ
        List<Category> categoryList = categoryService.findAllCategoriesList();

        // 店舗に設定している休日情報のリスト
        List<String> holidayCodes = new ArrayList<>();

        // ◆各条件に一つでも合致したら、エラーフラグをONにする◆

        // バリエーションエラーが存在するか
        if (bindingResult.hasErrors()) {
            System.out.println("バリデーションエラー：" + bindingResult.getAllErrors());
            hasErrors = true;
        }

        // カテゴリーIdの重複チェック 成功ならリストにいれる。 失敗ならエラーフラグOn
        if (!editForm.hasDuplicateCategory()) {
            categoryIds = editForm.getCategoryIds();
        } else {
            bindingResult.rejectValue("categoryId", "category.invalid", "複数の同じカテゴリーは適用できません。");
            hasErrors = true;
        }

        try {
            // 最低価格が最高価格を超えていないか
            if (!restaurantService.isValidPrices(editForm.getLowestPrice(), editForm.getHighestPrice())) {
                bindingResult.rejectValue("highestPrice", "price.range.invalid", "最高価格は最低価格以上にしてください。");
                hasErrors = true;
            }

            // 閉店時間が開店時間より前になっていないか
            if (!restaurantService.isValidBusinessHours(editForm.getOpeningTime(),
                    editForm.getClosingTime())) {
                bindingResult.rejectValue("closingTime", "business.hours.invalid", "閉店時間は開店時間より後にしてください。");
                hasErrors = true;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("不正な値が入力されました。: " + e.getMessage());
            hasErrors = true;
        }

        // エラーチェックを終えたら、Restaurantエンティティを作成して
        // Idに基づく店舗が存在していたら
        // フォームクラスの内容をつめこむ
        Restaurant restaurant = new Restaurant();

        try {
            restaurant = restaurantService.findRestaurantById(id);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            System.err.println(e.getMessage());

            return "redirect:/admin/restaurants";
        }

        restaurant.setId(id);
        restaurant.setName(editForm.getName());
        restaurant.setDescription(editForm.getDescription());
        restaurant.setPostalCode(editForm.getPostalCode());
        restaurant.setAddress(editForm.getAddress());
        restaurant.setLatitude(editForm.getLatitude());
        restaurant.setLongitude(editForm.getLongitude());
        restaurant.setOpeningTime(editForm.getOpeningTime());
        restaurant.setClosingTime(editForm.getClosingTime());
        restaurant.setHighestPrice(editForm.getHighestPrice());
        restaurant.setLowestPrice(editForm.getLowestPrice());
        restaurant.setSeatingCapacity(editForm.getSeatingCapacity());

        // 休日情報を、専用のフィールドに格納
        holidayCodes = editForm.getHolidayCodes();

        // エラーフラグがONになっている場合には、情報入力画面にもどる
        if (hasErrors) {

            model.addAttribute("regularHolidays", DayType.values());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("restaurant", restaurant);
            return "admin/restaurants/edit";
        }

        // 店舗情報の登録 イメージ画像が更新されていなかった場合は、エンティティの画像を更新しない
        try {
            MultipartFile imagFile = editForm.getImageFile();
            if (imagFile == null || imagFile.isEmpty()) {
                restaurant.setImage(editForm.getCurrentImageFileName());
                restaurantService.updateRestaurant(restaurant, null, categoryIds, holidayCodes);
            } else {
                restaurantService.updateRestaurant(restaurant, editForm.getImageFile(), categoryIds, holidayCodes);
            }
        } catch (

        IOException e) {
            System.err.println(new ImageStorageException("画像の保存に失敗しました。", e));
            bindingResult.reject("general.error", "予期せぬエラーが発生しました。もう一度お試しください。");

            model.addAttribute("regularHolidays", DayType.values());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("restaurant", restaurant);
            return "admin/restaurants/edit";
        }

        redirectAttributes.addFlashAttribute("successMessage", "店舗を編集しました。");
        return "redirect:/admin/restaurants/" + id;
    }

    // ユーザーの削除
    @PostMapping("/admin/restaurants/{id}/delete")
    public String delete(@PathVariable(value = "id") Integer id,
            RedirectAttributes redirectAttributes) {

        Restaurant restaurant;

        // 削除するユーザーを検索
        try {
            restaurant = restaurantService.findRestaurantById(id);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            System.err.println(e.getMessage());

            return "redirect:/admin/restaurants";
        }

        try {
            restaurantService.deleteRestaurant(restaurant);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗の削除に失敗しました。");
            System.err.println(e.getMessage());

            return "redirect:/admin/restaurants";
        }

        redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
        return "redirect:/admin/restaurants";
    }

}

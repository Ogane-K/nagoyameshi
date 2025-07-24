package com.example.nagoyameshi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.nagoyameshi.Dto.UserDto;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.entity.UserRoleId;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.mapper.UserMapper;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.UserRoleRepository;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    // コンストラクタインジェクション
    public UserService(PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            RoleRepository roleRepository) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    // ★ロジック系メソッド★

    // ユーザーアカウントを作成する
    @Transactional
    public void createUser(UserDto dto) {

        // 保存用ユーザーデータの雛形を用意
        User user = new User();

        // パスワードをハッシュ化
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // DTOの各値を雛形にセット
        user.setName(dto.getName());
        user.setFurigana(dto.getFurigana());
        user.setPostalCode(dto.getPostalCode());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());

        user.setEmailVerified(false);

        // 職業(Occupation)が空だったらnullをセット
        if (StringUtils.hasText(dto.getOccupation())) {
            user.setOccupation(dto.getOccupation());
        } else {
            user.setOccupation(null);
        }

        // 生年月日(Birthday)が空だったらnullをセット
        if (dto.getBirthday() != null) {
            user.setBirthday(dto.getBirthday());
        } else {
            user.setBirthday(null);
        }

        // ハッシュ化されたパスワードのセット
        user.setPassword(encodedPassword);

        // ユーザー情報をDBに保存
        userRepository.save(user);

        // ユーザーとロールの紐づけ情報の雛形を作成
        UserRole userRole = new UserRole();

        // userRoleの主キー
        UserRoleId userRoleId = new UserRoleId();

        // userRoleに格納用のロール情報を取得
        Role role = roleRepository.findByName("ROLE_FREE_MEMBER")
                .orElseThrow(() -> new RuntimeException("ロールが見つかりません"));

        // userRoleの主キーに値をセット
        userRoleId.setUserId(user.getId());
        userRoleId.setRoleId(role.getId());

        // 紐づけ情報を雛形にセット
        userRole.setId(userRoleId);
        userRole.setUser(user);
        userRole.setRole(role);

        // ユーザーとロールの紐づけ情報をDBに保存
        userRoleRepository.save(userRole);
    }

    // ユーザー情報の更新 メールアドレスがすでに存在しているものなら、失敗としてtrueを返す
    public boolean updateUser(UserEditForm form, Integer id) {

        User user = userRepository.findById(id).orElseThrow();

        String oldEmail = user.getEmail();

        user = UserMapper.mapToEntity(form, user);

        String newEmail = user.getEmail();

        if (!newEmail.equals(oldEmail) && userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    // メールアドレスが既に登録されているか されているならtrue
    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    // パスワードと確認用パスワードが一致しているか 一致してるならtrue
    public boolean isSamePassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    // ★検索系メソッド★

    // idに基づいてユーザーを探す
    public User findByUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした ID=" + id));
    }

    // emailに基づいてユーザーを探す
    public User findByUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした Email=" + email));
    }

    // ★ページング系検索メソッド★

    // 全ユーザーをページングされた状態で取得する
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // 氏名またはフリガナの一部と合致するユーザーをページングされた状態で取得する
    public Page<User> seachUsersByNameOrFurigana(String keyword, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCaseOrFuriganaContainingIgnoreCase(keyword, keyword, pageable);
    }

    // ★認証系メソッド★

    // ログイン中のロールを更新する
    public void refreshAuthenticationByRole(String newRole) {
        // 現在の認証情報を取得する
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // 新しい認証情報を作成する
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(newRole));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(currentAuthentication.getPrincipal(),
                currentAuthentication.getCredentials(), simpleGrantedAuthorities);

        // 認証情報を更新する
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

}

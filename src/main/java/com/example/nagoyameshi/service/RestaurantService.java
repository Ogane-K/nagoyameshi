package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.exception.DuplicateRestaurantNameException;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final HolidayService holidayService;

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryService restaurantCategoryService;

    public RestaurantService(RestaurantRepository restaurantRepository,
            RestaurantCategoryService restaurantCategoryService, HolidayService holidayService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantCategoryService = restaurantCategoryService;
        this.holidayService = holidayService;
    }

    // ★検索系メソッド★

    // 全店舗をページングされた状態で取得
    public Page<Restaurant> findallRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    // 全店舗を作成日時が新しい順にページングされた状態で取得
    public Page<Restaurant> findAllRestaurantsByOrderByCreatedAtDesc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // ランダムに6件取得
    public List<Restaurant> findRandom6Restaurants() {
        return restaurantRepository.findRandom6Restaurants();
    }

    // 指定されたキーワードを店舗名に含む店舗を、ページングされた状態で取得
    public Page<Restaurant> findRestaurantsByNameLike(String keyword, Pageable pageable) {
        return restaurantRepository.findBynameContaining(keyword, pageable);
    }

    // 指定したidを持つ店舗を取得
    public Restaurant findRestaurantById(Integer id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("店舗が見つかりません"));
    }

    // 店舗の合計レコード数を取得
    public long countRestaurants() {
        return restaurantRepository.count();
    }

    // idが最も大きい店舗を取得
    public Restaurant findFirstRestaurantByOrderByIdDesc() {
        return restaurantRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new RestaurantNotFoundException("店舗が見つかりません"));
    }

    // ★判別系メソッド★

    // 最高価格が最低価格より以上か 以上ならtrue 下ならfalse
    public boolean isValidPrices(Integer lowestPrice, Integer highestPrice) {

        if (lowestPrice == null || highestPrice == null) {
            return false;
        }

        return lowestPrice <= highestPrice;
    }

    // 開店時間が閉店時間よりも前か 前ならtrue あとならfalse
    public boolean isValidBusinessHours(LocalTime openingTime, LocalTime closingTime) {

        if (openingTime == null || closingTime == null) {
            return false;
        }

        return openingTime.isBefore(closingTime);
    }

    // ★書き込み系メソッド★

    // すでに登録されていない店舗がフォームから送信された場合に
    // 店舗をDBに登録する
    @Transactional
    public void createRestaurant(Restaurant restaurant,
            MultipartFile multipartFile,
            List<Integer> categoryIds,
            List<String> holidayCodes)
            throws IOException {

        if (restaurantRepository.existsByName(restaurant.getName())) {
            throw new DuplicateRestaurantNameException("その名前の店舗はすでに登録されています");
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            // ファイル名をUUID化
            String hashedImageName = generateNewFileName(multipartFile.getOriginalFilename());

            // 保存先のパスを作る
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);

            // 作ったパスにファイルを保存
            copyImageFile(multipartFile, filePath);

            // UUID化したファイルの名前をエンティティに保存
            restaurant.setImage(hashedImageName);
        }

        // DBに店舗情報を保存
        restaurantRepository.save(restaurant);

        // 休日と店舗の対比関係を保存する
        holidayService.updateHolidays(holidayCodes, restaurant);

        // カテゴリーと店舗の対比関係を保存する
        restaurantCategoryService.updateCategories(restaurant, categoryIds);

    }

    // 店舗情報をDBに更新する
    @Transactional
    public void updateRestaurant(Restaurant restaurant,
            MultipartFile multipartFile,
            List<Integer> categoryIds,
            List<String> holidayCodes)
            throws IOException {

        // 画像の更新処理
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // ファイル名をUUID化
            String hashedImageName = generateNewFileName(multipartFile.getOriginalFilename());

            // 保存先のパスを作る
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);

            // 作ったパスにファイルを保存
            copyImageFile(multipartFile, filePath);

            // UUID化したファイルの名前をエンティティに保存
            restaurant.setImage(hashedImageName);
        }

        System.out.println("現在の画像 :" + restaurant.getImage());

        // 情報の更新
        restaurantRepository.save(restaurant);

        // 休日と店舗の対比関係を保存する
        holidayService.updateHolidays(holidayCodes, restaurant);

        // カテゴリーと店舗の対比関係を更新する
        restaurantCategoryService.updateCategories(restaurant, categoryIds);
    }

    // 指定した店舗をDBから削除する
    @Transactional
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    // ★ロジック系★

    // UUIDを使って生成したファイル名を返す
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");

        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();
        }

        String hashedFileName = String.join(".", fileNames);

        return hashedFileName;
    }

    // 画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile, Path filePath) {
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

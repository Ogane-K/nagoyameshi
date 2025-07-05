package com.example.nagoyameshi.form;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RestaurantEditForm {

    @NotBlank(message = "店舗名を入力してください。")
    private String name;

    private MultipartFile imageFile;

    private String currentImageFileName;

    @NotBlank(message = "説明を入力してください。")
    private String description;

    @NotNull(message = "最低価格を選択してください。")
    private Integer lowestPrice;

    @NotNull(message = "最高価格を選択してください。")
    private Integer highestPrice;

    @NotBlank(message = "郵便番号を入力してください。")
    @Pattern(regexp = "^[0-9]{7}$", message = "郵便番号は7桁の半角数字で入力してください。")
    private String postalCode;

    @NotBlank(message = "住所を入力してください。")
    private String address;

    @NotNull(message = "開店時間を選択してください。")
    private LocalTime openingTime;

    @NotNull(message = "閉店時間を選択してください。")
    private LocalTime closingTime;

    @NotNull(message = "座席数を入力してください。")
    @Min(value = 0, message = "座席数は0席以上に設定してください。")
    private Integer seatingCapacity;

    private String regularHoliday;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @NotNull(message = "緯度を入力してください。")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @NotNull(message = "経度を入力してください。")
    private Double longitude;

    private Integer categoryId1;

    private Integer categoryId2;

    private Integer categoryId3;

    // カテゴリーIdをリスト化してゲットする
    public List<Integer> getCategoryIds() {

        List<Integer> ids = new ArrayList<>();

        // nullじゃないカテゴリーIdフィールドをリストにいれる
        if (categoryId1 != null)
            ids.add(categoryId1);
        if (categoryId2 != null)
            ids.add(categoryId2);
        if (categoryId3 != null)
            ids.add(categoryId3);

        return ids;
    }

    // カテゴリーidに重複がないかチェックする
    // @return true : 重複あり / false :重複なし
    public boolean hasDuplicateCategory() {
        List<Integer> ids = getCategoryIds();

        long distinctCount = ids.stream().distinct().count();

        return ids.size() != distinctCount;
    }

}

package com.example.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報登録 リクエストデータ
 */
@Data
public class UserAccountAddRequest implements Serializable {
	
    /**
     * ユーザー名
     */
    @NotEmpty(message = "ユーザー名を入力してください")
    @Size(max = 15, message = "名前は15桁以内で入力してください")
    private String accountId;
    
    /**
    * 郵便番号(3桁)
    */
   @NotEmpty(message = "上3桁の郵便番号を入力してください")
   @Size(max = 3, message = "上3桁の郵便番号は3桁以内で入力してください")
   private String postal1;
   
   /**
   * 郵便番号(4桁)
   */
   @NotEmpty(message = "下4桁の郵便番号を入力してください")
   @Size(max = 4, message = "下4桁の郵便番号は4桁以内で入力してください")
   private String postal2;
    
    /**
    * 住所
    */
   @NotEmpty(message = "住所を入力してください")
   @Size(max = 100, message = "住所は100桁以内で入力してください")
   private String address;
   
   /**
    * 電話番号
    */
   @Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号の形式で入力してください")
   private String phone;
   
   /**
   * パスワード
   */
   @NotEmpty(message = "パスワードを入力してください")
   @Size(max = 150, message = "パワスワードは150桁以内で入力してください")
   private String password;
   
   /**
   * 性別
   */
   @NotEmpty(message = "性別を入力してください")
   @Size(max = 5, message = "性別は5桁以内で入力してください")
   private String sex;
     
   /**
   * 画像
   */
   private MultipartFile iconImage;

}

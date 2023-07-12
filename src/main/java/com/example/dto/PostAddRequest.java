package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 投稿情報登録 リクエストデータ
 */
@Data
public class PostAddRequest {
	
    /**
     * 投稿名
     */
    @NotEmpty(message = "投稿名を入力してください")
    @Size(max = 20, message = "投稿名は20桁以内で入力してください")
    private String postName;
    
    /**
     * ジャンル
     */
    private String genre;
    
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
   * 投稿内容
   */
   private String message;
     
   /**
   * 投稿画像
   */
   private MultipartFile postImage;

}

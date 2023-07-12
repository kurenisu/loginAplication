package com.example.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;
/**
 * ユーザー情報 Entity
 */
@Data
@Component
@SessionScope
public class UserInfo implements Serializable {
	
    /**
     * ユーザーID
     */
    private String accountId;
    /**
     * 郵便番号
     */
    private String postCode;
    /**
     * 郵便番号(3桁表示用)
     */
    private String postal1;
    /**
     * 郵便番号(4桁表示用)
     */
    private String postal2;
    /**
     * 住所
     */
    private String address;
    /**
     * 電話番号
     */
    private String phone;
    
    /**
     * パスワード
     */
    private String password;
    
    /**
     * 性別
     */
    private String sex;
    /**
     * アイコン画像登録用
     */
    private byte[] iconImage;
    /**
     * アイコン画像表示用
     */
    private String iconImageView;
    /**
     * 更新日時
     */
    private Date updateDate;
    /**
     * 登録日時
     */
    private Date createDate;
    /**
     * 削除日時
     */
    private Date deleteDate;
    
    /**
     * アカウントロック
     */
    private String accountLock;
    /**
     * 削除日時
     */
    private Date accountExpirationDate;
    /**
     * 削除日時
     */
    private Date passwordExpirationDate;
    

}

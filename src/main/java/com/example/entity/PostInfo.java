package com.example.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;
/**
 * 投稿情報 Entity
 */
@Data
@Component
@SessionScope
public class PostInfo implements Serializable{
	
    /**
     * 投稿ID
     */
    private Long postId;
    /**
     * 投稿名
     */
    private String postName;
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
     * ジャンル
     */
    private String genre;
    
    /**
     * 投稿内容
     */
    private String message;
    /**
     * 投稿画像登録用
     */
    private byte[] postImage;
    /**
     * 投稿画像表示用
     */
    private String postImageView;
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

}

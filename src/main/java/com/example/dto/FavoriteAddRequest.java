package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * お気に入り情報登録 リクエストデータ
 */
@Data
public class FavoriteAddRequest {
	
    /**
     * アカウントID
     */
    @NotNull
    private String accountId;

	/**
     * 投稿ID
     */
    @NotNull
    private Long postId;

}

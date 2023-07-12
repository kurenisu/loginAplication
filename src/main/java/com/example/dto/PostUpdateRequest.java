package com.example.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 投稿情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostUpdateRequest extends PostAddRequest implements Serializable{
	
    /**
     * 投稿ID
     */
    @NotNull
    private Long postId;

}

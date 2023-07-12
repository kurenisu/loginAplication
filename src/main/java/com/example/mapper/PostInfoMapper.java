package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.PostInfo;

/**
 * 投稿情報 Mapper
 */
@Mapper
public interface PostInfoMapper {

    /**
     * 投稿情報新規登録
     * @param PostAddRequest 更新用リクエストデータ
     */
	public void save(PostInfo postAddRequest);
	
    /**
     * 投稿情報更新
     * @param PostUpdateRequest 更新用リクエストデータ
     */
    void update(PostInfo postUpdateRequest);
    
    /**
     * 投稿情報の論理削除
     * @param id ID
     */
    void delete(Long id);

	public List<PostInfo> findAll();
	
	public List<PostInfo> findId();
	
    /**
     * 投稿情報主キー検索
     * @param id 主キー
     * @return 検索結果
     */
     public PostInfo findById(Long id);

}

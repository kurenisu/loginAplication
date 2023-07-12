package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.UserInfo;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface UserInfoMapper {
	
    /**
     * ユーザーのアイコン情報主キー検索
     * @param id 主キー
     * @return 検索結果
     */
     public UserInfo findByIcon(String id);

     /**
      * デフォルトアイコン情報検索
      * @return 検索結果
      */
     public UserInfo findByDefaultIcon();
     
     /**
      * ユーザー情報主キー検索
      * @return 検索結果
      */
     public UserInfo findAll(String id);
     
     /**
      * ユーザー情報更新
      * @param UserAccountUpdateRequest 更新用リクエストデータ
      */
     void update(UserInfo userAccountUpdateRequest);

}

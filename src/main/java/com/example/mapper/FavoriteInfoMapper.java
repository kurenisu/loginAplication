package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.FavoriteInfo;

/**
 * お気に入り情報 Mapper
 */
@Mapper
public interface FavoriteInfoMapper {

    /**
     * お気に入り情報検索
     * @param accountId, postId 
     * @return 検索結果
     */
     public FavoriteInfo findByFavorite(String accountId, Long postId);
     
     /**
      * お気に入り情報登録
      * @param FavoriteAddRequest 登録用リクエストデータ
      */
     void save(FavoriteInfo favoriteAddRequest);
     
     /**
      * お気に入り情報全件検索
      * @param accountId, postId 
      * @return 検索結果
      */
      public List<FavoriteInfo> findByAllFavorite(String accountId);
}

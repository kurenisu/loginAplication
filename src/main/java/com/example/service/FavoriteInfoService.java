package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.FavoriteInfo;
import com.example.mapper.FavoriteInfoMapper;

/**
 * お気に入り情報 Service
 */
@Service
public class FavoriteInfoService {

  /**
  * お気に入り情報 Mapper
  */
 @Autowired
 private FavoriteInfoMapper favoriteInfoMapper;
 
/**
* お気に入り情報検索
* @param accountId, postId リクエストデータ
*/
public FavoriteInfo findByFavorite(String accountId, Long postId) throws IOException {
  return favoriteInfoMapper.findByFavorite(accountId, postId);
}

/**
 * お気に入り情報登録
 * @param favoriteAddRequest リクエストデータ
 */
public void saveAndUpdate(FavoriteInfo favoriteInfoAddRequest) {
	System.out.println("favorite_save1:" + favoriteInfoAddRequest);
	FavoriteInfo result = favoriteInfoMapper.findByFavorite(favoriteInfoAddRequest.getAccountId(), favoriteInfoAddRequest.getPostId());
	System.out.println("favorite_save2:" + result);
	if(result == null) {
		favoriteInfoAddRequest.setFavoriteFlag(true);
		favoriteInfoMapper.save(favoriteInfoAddRequest);
	} else {
		if(!result.getFavoriteFlag()) {
			System.out.println("favorite_save3:" + result.getFavoriteFlag());
			favoriteInfoAddRequest.setFavoriteFlag(true);
			favoriteInfoMapper.update(favoriteInfoAddRequest);			
		} else {
			System.out.println("favorite_save3:" + result.getFavoriteFlag());
			favoriteInfoAddRequest.setFavoriteFlag(false);
			favoriteInfoMapper.update(favoriteInfoAddRequest);
		}
	}
}

/**
* お気に入り情報全権検索
* @param accountId, postId リクエストデータ
*/
public List<FavoriteInfo> findByAllFavorite(String accountId) throws IOException {
  return favoriteInfoMapper.findByAllFavorite(accountId);
}
	
}

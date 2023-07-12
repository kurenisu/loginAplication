package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.PostInfo;
import com.example.mapper.PostInfoMapper;

/**
 * 投稿情報 Service
 */
@Service
public class PostInfoService {
	
    /**
     * 投稿情報 Mapper
     */
    @Autowired
    private  PostInfoMapper postInfoMapper;
    
    /**
     * 投稿情報全件検索
     * @return 検索結果
     */
    public List<PostInfo> findAll() {
        return postInfoMapper.findAll();
    }
    
    /**
     * 投稿ID全件検索
     * @return 検索結果
     */
    public List<PostInfo> findId() {
        return postInfoMapper.findId();
    }
    
    /**
     * 投稿情報主キー検索
     * @return 検索結果
     */
    public  PostInfo findById(Long id) {
        return postInfoMapper.findById(id);
    }
    
    /**
     * 投稿情報新規登録
     * @param PostAddRequest リクエストデータ
     */
    public void save(PostInfo postAddRequest) {
    	postInfoMapper.save(postAddRequest);
    }
    
    /**
     * 投稿情報更新
     * @param PostUpdateRequest リクエストデータ
     */
    public void update(PostInfo postUpdateRequest) {
        postInfoMapper.update(postUpdateRequest);
    }
    
    /**
     * 投稿情報論理削除
     * @param id
     */
    public void delete(Long id) {
        postInfoMapper.delete(id);
    }
	
	/* アップロードの実行処理 */
	public byte[] uploadFile(MultipartFile multipartFile) {
    	try {
        	// アップロードファイルをバイト値に変換
    		byte[] bytes = multipartFile.getBytes();
			
    		return bytes;
			
    	} catch (IOException e) {
        	e.printStackTrace();
    		return null;
    	}
	}

}

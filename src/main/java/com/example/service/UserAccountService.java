/**
 * 
 */
package com.example.service;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.UserInfo;
import com.example.mapper.AccountMapper;
import com.example.mapper.UserInfoMapper;

/**
 * ユーザー情報 Service
 */
@Service
public class UserAccountService {
	
    /**
     * ログイン情報 Mapper
     */
    @Autowired
    private AccountMapper accountMapper;
    
    /**
     * ユーザー情報 Mapper
     */
    @Autowired
    private UserInfoMapper userInfoMapper;;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    /**
     * ユーザー情報登録
     * @param userAccountAddRequest リクエストデータ
     */
    public void save(UserInfo userAccountAddRequest) {
    	//パスワードをハッシュ化
    	String hashedPassword = passwordEncoder.encode(userAccountAddRequest.getPassword());
    	userAccountAddRequest.setPassword(hashedPassword);
    	accountMapper.save(userAccountAddRequest);
    	accountMapper.roleSave(userAccountAddRequest);
    }
    
    /**
     * ユーザー情報更新
     * @param userAccountUpdateRequest リクエストデータ
     */
    public void update(UserInfo userAccountUpdateRequest) {
    	//パスワードをハッシュ化
    	String hashedPassword = passwordEncoder.encode(userAccountUpdateRequest.getPassword());
    	userAccountUpdateRequest.setPassword(hashedPassword);
    	userInfoMapper.update(userAccountUpdateRequest);
    }
    
    /**
     * ユーザーアイコン検索
     * @param id リクエストデータ
     */
    public String findByIcon(String id) throws IOException {
    	System.out.println("id:" + id);
    	UserInfo user = userInfoMapper.findByIcon(id);
        
        if(user == null) {
        	user = userInfoMapper.findByDefaultIcon();
        	user.setIconImageView(imageEncoder(user.getIconImage()));
    		
        } else if(user.getIconImage() != null) {
    		user.setIconImageView(imageEncoder(user.getIconImage()));

        }
    	
        return user.getIconImageView();
    }
    
    /**
     * ユーザー情報検索
     * @param id リクエストデータ
     */
    public UserInfo findAll(String id) throws IOException {
    	UserInfo user = userInfoMapper.findAll(id);
        user.setPostal1(user.getPostCode().substring(0,3));
        user.setPostal2(user.getPostCode().substring(3));
        return user;
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
	
	/* バイナリー画像をbase64へエンコード実行処理 */
	public String imageEncoder(byte[] image) {
    	String base64 = new String(Base64.encodeBase64String(image));
    	System.out.println("base64:" + base64);
		// 拡張子をjpegと指定
        // <img ht:src="">で指定できる形にする
    	StringBuffer data = new StringBuffer();
    	String format = "";
    	// 画像のフォーマットによって形式変換
    	if(base64.startsWith("iVBOR")) {
    		format = "data:image/png;base64,";
    	}else if(base64.startsWith("R0lGO")) {
    		format = "data:image/gif;base64,";
    	}else if(base64.startsWith("/9j/4")) {
    		format = "data:image/jpeg;base64,";
    	}else if(base64.startsWith("PHN2")) {
    		format = "data:image/svg+xml;base64,";
    	}
		data.append(format);
		data.append(base64);
		return data.toString();
	}

}

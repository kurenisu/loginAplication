package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.UserAccountUpdateRequest;
import com.example.entity.FavoriteInfo;
import com.example.entity.UserInfo;
import com.example.service.FavoriteInfoService;
import com.example.service.UserAccountService;

import jakarta.validation.Valid;

/**
 * ユーザー情報 Controller
 */
@Controller
@RequestMapping("/")
public class UserController {
	
	/**
	 * ユーザー情報 Service
	**/
	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * お気に入り情報 Service
	**/
	@Autowired
	private FavoriteInfoService favoriteInfoService;
	
	@Autowired
	private UserInfo user;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
    /**
     * マイページ画面を表示
     * @return マイページ画面
     * @throws IOException 
     */
	@GetMapping("/user/mypage")
	public String mypage(Model model, Authentication auth) throws IOException {
		
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        
        UserInfo result = userAccountService.findAll(auth.getName());
        System.out.println(result);
        model.addAttribute("user", result);
		return "/user/mypage";
	}

    /**
     * お気に入り画面を表示
     * @return お気に入り画面
     * @throws IOException 
     */
	@GetMapping("/user/favorite")
	public String favorite(Model model, Authentication auth) throws IOException {
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        
		List<FavoriteInfo> info = favoriteInfoService.findByAllFavorite(auth.getName());
		
        StringBuffer data;
        
        String base64 = "";
        
        for(int i=0; i<info.size(); i++) {
        	
        	if(info.get(i).getPostImage() != null) {
        		
        		// base64にエンコードしたものを文字列に変更
        		base64 = new String(Base64.encodeBase64String(info.get(i).getPostImage()));
        		// 拡張子をjpegと指定
                // <img ht:src="">で指定できる形にする
        		data = new StringBuffer();
        		data.append("data:image/png;base64,");
        		data.append(base64);
        		info.get(i).setPostImageView(data.toString());
        	}
        }
        model.addAttribute("favoritelist", info);
        
		return "/user/favorite";
	}
	
    /**
     * ユーザー情報編集画面を表示
     * @return ユーザー情報編集画面
     * @throws IOException 
     */
	@GetMapping("/user/{id}/useredit")
	public String userEdit(Model model, Authentication auth) throws IOException {
        UserInfo userEdit = userAccountService.findAll(auth.getName());
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest();
        userAccountUpdateRequest.setAccountId(userEdit.getAccountId());
        userAccountUpdateRequest.setPhone(userEdit.getPhone());
        userAccountUpdateRequest.setPostal1(userEdit.getPostCode().substring(0,3));
        userAccountUpdateRequest.setPostal2(userEdit.getPostCode().substring(3));
        userAccountUpdateRequest.setAddress(userEdit.getAddress());
        userAccountUpdateRequest.setSex(userEdit.getSex());
        userAccountUpdateRequest.setPassword("");
        model.addAttribute("userAccountUpdateRequest", userAccountUpdateRequest);
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
		return "/user/useredit";
	}
	
    /**
     * ユーザー情報更新
     * @param userAccountUpdateRequest リクエストデータ
     * @param model Model
     * @return マイページ
     * @throws IOException 
     */
    @PostMapping("/user/useredit")
    public String update(@Valid @ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, BindingResult result, Model model) throws IOException {
    	logger.info("user is {}", userAccountUpdateRequest);
    	logger.info("result is {}", result);
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
            return "/user/useredit";
        }
        // userAccountAddRequestをUserInfoクラスに変換
        UserInfo info = modelMapper.map(userAccountUpdateRequest, UserInfo.class);
        
        info.setPostCode(userAccountUpdateRequest.getPostal1()+userAccountUpdateRequest.getPostal2());
        
        if(!userAccountUpdateRequest.getIconImage().isEmpty()) {
            // フォームに渡されたアップロードファイルを取得
            MultipartFile multipartFile = userAccountUpdateRequest.getIconImage();

            // アップロード実行処理メソッドの呼び出し
            info.setIconImage(userAccountService.uploadFile(multipartFile));
        }
        logger.info("info is {}", info);
        // ユーザー情報の更新
        userAccountService.update(info);
        return "redirect:/user/mypage";
    }
	
}

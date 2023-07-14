package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.PostAddRequest;
import com.example.dto.PostUpdateRequest;
import com.example.entity.PostInfo;
import com.example.entity.UserInfo;
import com.example.service.PostInfoService;

import jakarta.validation.Valid;

/**
 * 管理者 Controller
 */
@Controller
@RequestMapping("/")
public class AdminController {
	
	/**
	 * 投稿情報 Service
	**/
	@Autowired
	private PostInfoService postInfoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserInfo user;
	
	MultipartFile multiInputStream;
	
	Logger logger = LoggerFactory.getLogger(loginController.class);
	
    /**
     * 管理画面を表示
     * @return 管理画面
     */
	@GetMapping("/admin/admin")
	public String admin(Model model) {
		List<PostInfo> postIdList = postInfoService.findId();
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        model.addAttribute("postIdList", postIdList);
		return "admin/admin";
	}
	
    /**
     * 投稿画面を表示
     * @return 投稿画面
     */
	@GetMapping("/admin/postpage")
	public String postpage(@ModelAttribute PostAddRequest postAddRequest, Model model) {
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
		return "admin/postpage";
	}
	
    /**
     * 投稿詳細画面を表示
     * @param id ID
     * @param model Model
     * @return 投稿詳細画面
     */
    @GetMapping("/admin/{id}/postdetail")
    public String displayDetail(@PathVariable Long id, Model model) {
        PostInfo post = postInfoService.findById(id);

        post.setPostal1(post.getPostCode().substring(0,3));
        post.setPostal2(post.getPostCode().substring(3));
        
        StringBuffer data;
        
        String base64 = "";
        
    	if(post.getPostImage() != null) {
    		// base64にエンコードしたものを文字列に変更
    		base64 = new String(Base64.encodeBase64String(post.getPostImage()));
    		// 拡張子をjpegと指定
            // <img ht:src="">で指定できる形にする
    		data = new StringBuffer();
    		data.append("data:image/png;base64,");
    		data.append(base64);
    		post.setPostImageView(data.toString());
    	}
		logger.info("postresult is {}", post);
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        model.addAttribute("postDetail", post);
        return "admin/postdetail";
    }
	
    /**
     * 投稿編集画面を表示
     * @param id ID
     * @param model Model
     * @return 投稿編集画面
     */
    @GetMapping("/admin/{id}/postedit")
    public String displayEdit(@PathVariable Long id, Model model) {
        PostInfo postEdit = postInfoService.findById(id);
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest();
        postUpdateRequest.setPostId(postEdit.getPostId());
        postUpdateRequest.setPostName(postEdit.getPostName());
        postUpdateRequest.setPhone(postEdit.getPhone());
        postUpdateRequest.setPostal1(postEdit.getPostCode().substring(0,3));
        postUpdateRequest.setPostal2(postEdit.getPostCode().substring(3));
        postUpdateRequest.setAddress(postEdit.getAddress());
        postUpdateRequest.setMessage(postEdit.getMessage());
        postUpdateRequest.setGenre(postEdit.getGenre());
        model.addAttribute("postUpdateRequest", postUpdateRequest);
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        return "admin/postedit";
    }
	
    /**
     * 投稿新規登録
     * @param postAddRequest リクエストデータ
     * @param model Model
     * @return 投稿画面
     * @throws IOException 
     */
    @PostMapping("/admin/postpage")
    public String create(@Valid @ModelAttribute PostAddRequest postAddRequest, BindingResult result, Model model) throws IOException {
    	logger.info("date is {}", postAddRequest);
    	if (result.hasErrors()) {
            // 入力チェックエラーの場合
            return "/admin/postpage";
        }
        // userAccountAddRequestをUserInfoクラスに変換
        PostInfo info = modelMapper.map(postAddRequest, PostInfo.class);
        
        info.setPostCode(postAddRequest.getPostal1()+postAddRequest.getPostal2());
        
        if(!postAddRequest.getPostImage().isEmpty()) {
            // フォームに渡されたアップロードファイルを取得
            MultipartFile multipartFile = postAddRequest.getPostImage();

            // アップロード実行処理メソッドの呼び出し
            info.setPostImage(postInfoService.uploadFile(multipartFile));
        }

        // 投稿情報の登録
        postInfoService.save(info);
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        return "redirect:/admin/admin";
    }
    
    /**
     * 投稿更新
     * @param postUpdateRequest リクエストデータ
     * @param model Model
     * @return 投稿情報詳細画面
     */
    @PostMapping("/admin/postedit")
    public String update(@Valid @ModelAttribute PostUpdateRequest postUpdateRequest, BindingResult result, Model model) {
    	logger.info("result is {}", result);
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "/admin/postedit";
        }
        // postUpdateRequestをPostInfoクラスに変換
        PostInfo info = modelMapper.map(postUpdateRequest, PostInfo.class);
        
        info.setPostCode(postUpdateRequest.getPostal1()+postUpdateRequest.getPostal2());
        
        if(!postUpdateRequest.getPostImage().isEmpty()) {
            // フォームに渡されたアップロードファイルを取得
            MultipartFile multipartFile = postUpdateRequest.getPostImage();
            
            // アップロード実行処理メソッドの呼び出し
            info.setPostImage(postInfoService.uploadFile(multipartFile));
        }

        // 投稿情報の更新
        postInfoService.update(info);
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        return "redirect:/admin/admin";
    }
    
    /**
     * 投稿情報削除（論理削除）
     * @param id ID
     * @param model Model
     * @return 管理画面
     */
    @GetMapping("/admin/{id}/postdelete")
    public String delete(@PathVariable Long id, Model model) {
        // 投稿情報の削除
        postInfoService.delete(id);
        return "redirect:/admin/admin";
    }

}

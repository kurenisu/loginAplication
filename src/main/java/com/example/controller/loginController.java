package com.example.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.UserAccountAddRequest;
import com.example.entity.FavoriteInfo;
import com.example.entity.PostInfo;
import com.example.entity.UserInfo;
import com.example.service.FavoriteInfoService;
import com.example.service.PostInfoService;
import com.example.service.UserAccountService;

import jakarta.validation.Valid;

/**
 * アカウント情報 Controller
 */
@Controller
@RequestMapping("/")
public class loginController {
	
	/**
	 * ユーザー情報 Service
	**/
	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * 投稿情報 Service
	**/
	@Autowired
	private PostInfoService postInfoService;
	
	/**
	 * お気に入り情報 Service
	**/
	@Autowired
	private FavoriteInfoService favoriteInfoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserInfo user;
	
	@Autowired
	private List<PostInfo> post;
	
	MultipartFile multiInputStream;
	
	Logger logger = LoggerFactory.getLogger(loginController.class);

    /**
     * トップ画面を表示
     * @return トップ画面
     * @throws IOException 
     */
	@GetMapping("/toppage")
	public String toppage(Model model, Authentication auth) throws IOException {
		post = postInfoService.findAll();
		user.setIconImageView(userAccountService.findByIcon(auth.getName()));
		
        model.addAttribute("base64AccountIcon",user.getIconImageView().toString());
        
        StringBuffer data;
        
        String base64 = "";
        
        for(int i=0; i<post.size(); i++) {
        	
        	if(post.get(i).getPostImage() != null) {
        		
        		// base64にエンコードしたものを文字列に変更
        		base64 = new String(Base64.encodeBase64String(post.get(i).getPostImage()));
        		// 拡張子をjpegと指定
                // <img ht:src="">で指定できる形にする
        		data = new StringBuffer();
        		data.append("data:image/png;base64,");
        		data.append(base64);
        		post.get(i).setPostImageView(data.toString());
        	}
        }
        model.addAttribute("postlist", post);
		return "/toppage";
	}
		
    /**
     * 投稿詳細画面を表示
     * @param id ID
     * @param model Model
     * @return 投稿詳細画面
     */
    @GetMapping("/{id}/postinfo")
    public String displayInfo(@PathVariable Long id, Model model) {
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
        model.addAttribute("base64AccountIcon", user.getIconImageView().toString());
        model.addAttribute("postInfo", post);
        return "/postinfo";
    }
	
    /**
     * ログイン画面を表示
     * @return ログイン画面
     */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
    /**
     * 新規登録画面を表示
     * @return 新規登録画面
     */
	@GetMapping("/signup")
	public String signup(@ModelAttribute UserAccountAddRequest userAccountAddRequest, Model model) {
		model.addAttribute("userAccountAddRequest", new UserAccountAddRequest());
		return "signup";
	}
	
    /**
     * お気に入りボタン
     * @return トップ画面
     * @throws IOException 
     */
	@GetMapping("/{id}/toppage")
	public String favoriteBtn(@PathVariable Long id, Model model, Authentication auth, FavoriteInfo favorite) throws IOException {
		favorite.setAccountId(auth.getName());
		favorite.setPostId(id);
		favoriteInfoService.saveAndUpdate(favorite);
		model.addAttribute("base64AccountIcon", user.getIconImageView().toString());
		model.addAttribute("postlist", post);
		return "/toppage";
	}
	
    /**
     * ユーザー新規登録
     * @param userAccountAddRequest リクエストデータ
     * @param model Model
     * @return ログイン画面
     * @throws IOException 
     */
    @PostMapping("/signup")
    public String create(@Valid @ModelAttribute UserAccountAddRequest userAccountAddRequest, BindingResult result, Model model) throws IOException {

    	if (result.hasErrors()) {
            // 入力チェックエラーの場合
            return "/signup";
        }
        // userAccountAddRequestをUserInfoクラスに変換
        UserInfo info = modelMapper.map(userAccountAddRequest, UserInfo.class);
        
        info.setPostCode(userAccountAddRequest.getPostal1()+userAccountAddRequest.getPostal2());
        info.setAccountLock("1");
        
        // Timestamp型のシステム日時を取得
        Timestamp nowDttm = new Timestamp(System.currentTimeMillis());
        // Date型へ変換
        Date dt = new Date(nowDttm.getTime());
        // Calendarクラスのインスタンスを生成
        Calendar cal = Calendar.getInstance();        
        // 1年加算
        cal.setTime(dt);
        cal.add(Calendar.YEAR, 1);
        dt = cal.getTime();
    	logger.info("date is {}", dt);
        info.setAccountExpirationDate(dt);
        info.setPasswordExpirationDate(dt);

        if(!userAccountAddRequest.getIconImage().isEmpty()) {
            // フォームに渡されたアップロードファイルを取得
            MultipartFile multipartFile = userAccountAddRequest.getIconImage();

            // アップロード実行処理メソッドの呼び出し
            info.setIconImage(userAccountService.uploadFile(multipartFile));
        }

        // 店舗情報の登録
        userAccountService.save(info);
        return "redirect:/login";
    }

}

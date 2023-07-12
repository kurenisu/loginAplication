package com.example.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.LoginAccount;
import com.example.mapper.AccountMapper;

/**
 * アカウント情報 Service
 */
@Service
public class AccountUserDetailsService implements UserDetailsService {
	
	@Autowired
	AccountMapper mapper;
	
	Logger logger = LoggerFactory.getLogger(AccountUserDetailsService.class);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginAccount data = mapper.getAccount(username);
		logger.info("data is {}", data);
		
		if (data == null) {
			throw new UsernameNotFoundException("not found : " + username);
		}
		
		// LocaleDate型をDate型へ変換
		Date today = Date.from( LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		UserDetails user = null;
		
		if(data.roleName().equals("ROLE_ADMIN")) {
			 user = User.withUsername(data.accountId())
					.password(data.password())
					.roles("ADMIN", "USER")
					// ユーザーアカウントが無効かチェック
					.disabled(!data.accountLock())
					// ユーザーアカウントの有効期限が切れているかチェック
					.accountExpired(data.accountExpirationDate().before(today))
					// パスワードの有効期限が切れているかチェック
					.credentialsExpired(data.passwordExpirationDate().before(today))
					.build();
		
		}else {
			 user = User.withUsername(data.accountId())
					.password(data.password())
					.roles("USER")
					// ユーザーアカウントが無効かチェック
					.disabled(!data.accountLock())
					// ユーザーアカウントの有効期限が切れているかチェック
					.accountExpired(data.accountExpirationDate().before(today))
					// パスワードの有効期限が切れているかチェック
					.credentialsExpired(data.passwordExpirationDate().before(today))
					.build();
			
		}
		return (user);	
				
	}

}

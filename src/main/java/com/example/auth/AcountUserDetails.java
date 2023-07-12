package com.example.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.LoginAccount;

public class AcountUserDetails implements UserDetails {
	
	private final LoginAccount user;
	
	public AcountUserDetails(LoginAccount user) {
		this.user = user;
	}

	public LoginAccount getAcountUserDetalis() {
		return this.user;
		
	} 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/** 
	 * UserAccountから取得したpasswordを返す
	 * */
	@Override
	public String getPassword() {
		return this.user.password();
	}

	/** 
	 * UserAccountから取得したaccountIdを返す
	 * */
	@Override
	public String getUsername() {
		return this.user.accountId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}

package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.entity.LoginAccount;
import com.example.entity.UserInfo;

/**
 * アカウント情報 Mapper
 */
@Mapper
public interface AccountMapper {
	
	public LoginAccount getAccount(String accountId);
	
	public void save(UserInfo userAccountAddRequest);
	
	public void roleSave(UserInfo userAccountAddRequest);


}

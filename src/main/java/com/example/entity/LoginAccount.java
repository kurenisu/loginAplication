package com.example.entity;

import java.util.Date;

/**
 * アカウント情報 Entity
 */
public record LoginAccount(String accountId, String password, Boolean accountLock, Date accountExpirationDate, Date passwordExpirationDate, Date createDate, Date updateDate, String roleName) {}

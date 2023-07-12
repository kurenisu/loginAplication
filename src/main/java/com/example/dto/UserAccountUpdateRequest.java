package com.example.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * ユーザー情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAccountUpdateRequest extends UserAccountAddRequest implements Serializable {}

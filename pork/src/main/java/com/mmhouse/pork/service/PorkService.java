package com.mmhouse.pork.service;

import java.util.List;
import java.util.Map;
 
public interface PorkService {
	
	/*
	 * 로그인 처리
	 */
	Map<String, Object> userLogin(Map<String, Object> commandMap);
	
	/*
	 * 회원가입 이메일 중복 체크
	 */
	Map<String, Object> checkUser(Map<String, Object> commandMap);
	
	/*
	 * 회원가입 처리
	 */
	int joinUser(Map<String, Object> commandMap);

}
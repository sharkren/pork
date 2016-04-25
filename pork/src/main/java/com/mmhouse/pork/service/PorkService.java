package com.mmhouse.pork.service;

import java.util.List;
import java.util.Map;

import com.mmhouse.pork.vo.ContentsMediaVO;
import com.mmhouse.pork.vo.ContentsVO;
import com.mmhouse.pork.vo.UserVO;
 
public interface PorkService {
	
	/*
	 * 로그인 처리
	 */
	//Map<String, Object> userLogin(Map<String, Object> commandMap);
	UserVO userLogin(UserVO userVo);
	
	/*
	 * 회원가입 이메일 중복 체크
	 */
	Map<String, Object> checkUser(Map<String, Object> commandMap);
	
	/*
	 * 회원가입 처리
	 */
	int joinUser(Map<String, Object> commandMap);
	
	
	/*
	 * content 등록
	 */
	int writeContent(ContentsVO contentsVo);
	
	/*
	 * content id 조회
	 */
	Map<String, Object> getContId(ContentsVO contentsVo);
	
	/*
	 * content media등록 
	 */
	int writeContentMedia(ContentsMediaVO contentsMediaVo);
	
}
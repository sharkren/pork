package com.mmhouse.pork.dao;
 
import java.util.List;
import java.util.Map;

import org.mariadb.jdbc.internal.SQLExceptionMapper;
import org.springframework.stereotype.Repository;
 



import com.mmhouse.pork.common.dao.*;
 
@Repository("porkDAO")
public class PorkDAO extends AbstractDAO{

	/*
	 * 로그인 처리
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> userLogin(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return (Map<String, Object>)selectOne("pork.userLogin", commandMap);
	}
	
	/*
	 * 회원가입 이메일 중복 체크
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkUser(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return (Map<String, Object>)selectOne("pork.checkUser", commandMap);
	}
	
	/*
	 * 회원가입 처리
	 */
	@SuppressWarnings("unchecked")
	public int joinUser(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return (int)insert("pork.joinUser", commandMap); 
	}
}
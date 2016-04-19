package com.mmhouse.pork.service;
 
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmhouse.pork.dao.PorkDAO;
 
@Service("porkService")
public class PorkServiceImpl implements PorkService {

	@Resource(name="porkDAO")
    private PorkDAO porkDAO;
	
	/*
	 * 로그인 처리
	 */
	@Override
	public Map<String, Object> userLogin(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return porkDAO.userLogin(commandMap);
	}
	
	/*
	 * 회원가입 이메일 중복 체크
	 */
	@Override
	public Map<String, Object> checkUser(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return porkDAO.checkUser(commandMap);
	}
	
	/*
	 * 회원가입 처리
	 */
	@Override
	public int joinUser(Map<String, Object> commandMap) {
		// TODO Auto-generated method stub
		return porkDAO.joinUser(commandMap);
	}
	
}
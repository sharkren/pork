package com.mmhouse.pork.service;
 
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmhouse.pork.dao.PorkDAO;
import com.mmhouse.pork.vo.ContentsMediaVO;
import com.mmhouse.pork.vo.ContentsVO;
import com.mmhouse.pork.vo.UserVO;
 
@Service("porkService")
public class PorkServiceImpl implements PorkService {

	@Resource(name="porkDAO")
    private PorkDAO porkDAO;
	
	/*
	 * 로그인 처리
	 */
	@Override
	//public Map<String, Object> userLogin(Map<String, Object> commandMap) {
	public UserVO userLogin(UserVO userVo) {
		// TODO Auto-generated method stub
		return porkDAO.userLogin(userVo);
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
	
	
	/*
	 * content 등록
	 */
	@Override
	public int writeContent(ContentsVO contentsVo) {
		// TODO Auto-generated method stub
		return porkDAO.writeContent(contentsVo);
	}
	
	/*
	 * content id 조회
	 */
	@Override
	public Map<String, Object> getContId(ContentsVO contentsVo) {
		// TODO Auto-generated method stub
		return porkDAO.getContId(contentsVo);
	}
	
	
	/*
	 * content media등록 
	 */
	@Override
	public int writeContentMedia(ContentsMediaVO contentsMediaVo) {
		// TODO Auto-generated method stub
		return porkDAO.writeContentMedia(contentsMediaVo);
	}
	
}
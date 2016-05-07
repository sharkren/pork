package com.mmhouse.pork.dao;
 
import java.util.List;
import java.util.Map;

import org.mariadb.jdbc.internal.SQLExceptionMapper;
import org.springframework.stereotype.Repository;
 









import com.mmhouse.pork.common.dao.*;
import com.mmhouse.pork.vo.ContentsMediaVO;
import com.mmhouse.pork.vo.ContentsVO;
import com.mmhouse.pork.vo.UserVO;
 
@Repository("porkDAO")
public class PorkDAO extends AbstractDAO{

	/*
	 * 로그인 처리
	 */
	@SuppressWarnings("unchecked")
	//public Map<String, Object> userLogin(Map<String, Object> commandMap) {
	public UserVO userLogin(UserVO userVo) {
		// TODO Auto-generated method stub
		return (UserVO)selectOne("pork.userLogin", userVo);
	}
	
	/*
	 * 회원가입 이메일 중복 체크
	 */
	@SuppressWarnings("unchecked")
	public int checkUser(UserVO userVo) {
		// TODO Auto-generated method stub
		return (int)selectOne("pork.checkUser", userVo);
	}
	
	/*
	 * 회원가입 처리
	 */
	@SuppressWarnings("unchecked")
	public int joinUser(UserVO userVo) {
		// TODO Auto-generated method stub
		return (int)insert("pork.joinUser", userVo); 
	}
	
	/*
	 * 콘텐츠 등록
	 */
	public int writeContent(ContentsVO contentsVo) {
		// TODO Auto-generated method stub
		return (int)insert("pork.writeContent", contentsVo);
	}
	
	/*
	 * 콘텐츠 첨부파일 등록
	 */
	public int writeContentMedia(ContentsMediaVO contentsMediaVo) {
		// TODO Auto-generated method stub
		return (int)insert("pork.writeContentMedia", contentsMediaVo);
	}

	/*
	 * 콘텐츠 ID조회
	 */
	public Map<String, Object> getContId(ContentsVO contentsV0) {
		// TODO Auto-generated method stub
		return (Map<String, Object>)selectOne("pork.getContId", contentsV0);
	}
}
package com.mmhouse.pork.service;
 
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.mmhouse.pork.dao.PorkDAO;
import com.mmhouse.pork.vo.ContentsMediaVO;
import com.mmhouse.pork.vo.ContentsVO;
import com.mmhouse.pork.vo.UserVO;
 
@Service("porkService")
public class PorkServiceImpl implements PorkService {
	
	Logger log = Logger.getLogger(this.getClass());
	
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
	public int checkUser(UserVO userVo) {
		// TODO Auto-generated method stub
		return porkDAO.checkUser(userVo);
	}
	
	/*
	 * 회원가입 처리
	 */
	@Override
	public int joinUser(UserVO userVo) {
		// TODO Auto-generated method stub
		return porkDAO.joinUser(userVo);
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
	
	@Override
	public boolean fileUpload(MultipartHttpServletRequest request) {
		
		log.info("PORK_LOG >>> ServiceImpl fileUpload STRAT >>> ");
		
		boolean isSuccess = false;

		String uploadPath = "E:/file/upload/";

		File dir = new File(uploadPath);

		if (!dir.isDirectory()) {
			log.debug("PORK_LOG >>> ServiceImpl fileUpload Create Directory");
			dir.mkdirs();
		}
		
		Iterator<String> iterator = request.getFileNames();
        
		String strParam = request.getParameter("jsonData");
		log.debug("PORK_LOG >>> ServiceImpl param json " + strParam);
		
		// Contents Data 추출
		Gson gson = new Gson();
        ContentsVO contentsVo = gson.fromJson(strParam, ContentsVO.class);
        log.debug("PORK_LOG >>> ServiceImpl contentsVo string :  " + gson.toJson(contentsVo).toString());
        
        // Media Data 리스트 추출
        List<ContentsMediaVO> contMediaList = (List<ContentsMediaVO>)contentsVo.getContentsMedia();
        log.debug("PORK_LOG >>> ServiceImpl contMediaList Count :  " + contMediaList.size());
        
		while(iterator.hasNext()) {
			
			String uploadFileName = iterator.next();
			
			log.debug("PORK_LOG >>> ServiceImpl uploadFileName " + uploadFileName);
			
			MultipartFile mFile = request.getFile(uploadFileName);
			String originalFileName = mFile.getOriginalFilename();
			String saveFileName = originalFileName;

			if(saveFileName != null && !saveFileName.equals("")) {

				if(new File(uploadPath + saveFileName).exists()) {
					saveFileName = saveFileName + "_" + System.currentTimeMillis();
				}

				try {
					mFile.transferTo(new File(uploadPath + saveFileName));
					isSuccess = true;				
				} catch (IllegalStateException e) {
					e.printStackTrace();
					isSuccess = false;
				} catch (IOException e) {
					e.printStackTrace();
					isSuccess = false;
				}
			} // if end
		} // while end
		
		// file upload 후에 content 등록
    	int wirteResult = 0;
    	Map<String,Object> retMap;
    	
    	// contents table에 데이터 등록
    	wirteResult = this.writeContent(contentsVo);
    	log.debug("PORK_LOG >>> writeContent wirteResult  \t:  " + wirteResult);
    	
    	if (wirteResult == 1) {
    		
    		// 등록된 table에서 contents id 조회
	        retMap = this.getContId(contentsVo);        	
    		
    		if (contMediaList.size() > 0) {
    			
    			Iterator it = contMediaList.iterator();
        		
            	while(it.hasNext()) {
            		
            		ContentsMediaVO contentsMediaVo = (ContentsMediaVO)it.next();
            		contentsMediaVo.setContId(String.valueOf(retMap.get("contId")));
            		contentsMediaVo.setContType(contentsVo.getContType());
            		
            		// 조회된 content id로 contents_media 테이블 데이터 등록
        	        wirteResult = this.writeContentMedia(contentsMediaVo);

            	}
    		}
    	}
    	else {
    		isSuccess = false;
    	}
		
		log.info("PORK_LOG >>> ServiceImpl fileUpload END >>> ");
		
		return isSuccess;
	} // fileUpload end
	
}
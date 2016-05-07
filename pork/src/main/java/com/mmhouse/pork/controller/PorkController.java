package com.mmhouse.pork.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mmhouse.pork.controller.util.Util;
import com.mmhouse.pork.service.PorkService;
import com.mmhouse.pork.vo.ContentsMediaVO;
import com.mmhouse.pork.vo.ContentsVO;
import com.mmhouse.pork.vo.UserVO;

@Controller
public class PorkController {
	
	Logger log = Logger.getLogger(this.getClass());
    
	private UserVO user;;
	private Util util = new Util();
	private Gson gson = new Gson();
	
    @Resource(name="porkService")
    private PorkService porkService;
    
    // RequestMapping을 이용한 URL mapping. / PorkController 다음에 /jsonData가 오면 아래 메서드로 매핑된다.
    // 예) http://localhost/프로젝트명/ PorkController /jsonData
    // 또한 RequestMethod.POST 정의를 통해, POST방식으로 요청을 받게끔 정의한다. (GET 요청은 매핑 안시켜줌)
    // consumes를 통해 POST방식으로 넘어온 요청에서 json 형태로 postData를 넘겨줌을 정의
    
    /**
     * 
     * @param request
     * @return response
     * @throws Exception
     * @since  2016.03.27
     * @author thomas
     * @refer url : http://addio3305.tistory.com/79
     * 
     */
    @RequestMapping(value="/api/userLogin.do", method = RequestMethod.POST)
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
    	log.info("PORK_LOG >>> userLogin STRAT >>> ");
    	
    	String param = null;
        param = util.getReqData(request);
        
        log.debug("PORK_LOG >>> userLogin Request param  \t:  " + param);
        
        UserVO userVo = gson.fromJson(param, UserVO.class);
        
        JsonObject json =  new JsonObject();; 
        
        try {
        	
        	UserVO userInfo = null;
             if ( !"".equals(userVo.getEmail()) && !"".equals(userVo.getPwd()) ) {
             	userInfo = porkService.userLogin(userVo);

             	if (userInfo != null) {
             		// Return Data를 Json String으로 변환
             		String jsonString = new Gson().toJson(userInfo);
                	log.info("PORK_LOG >>> userLogin Response Param [" + jsonString.toString() + "]");
                	json = new JsonParser().parse(jsonString).getAsJsonObject();
                	log.info("PORK_LOG >>> userLogin Response json [" + json.toString() + "]");
                	json.addProperty("result_mgs", "success");
                	json.addProperty("result_code", "S0000");
                }
                else {
                	
                	json.addProperty("result_mgs", "data not found");
                	json.addProperty("result_code", "E0001");
                }
             }
             else {
             	
             	json.addProperty("result_mgs", "로그인 정보가 부족합니다.");
             	json.addProperty("result_code", "E0002");
             	
             }
             
        }
        catch (Exception e) {
        	
        	json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "9999");
        	log.debug("PORK_LOG >>> 로그인 오류", e.getCause());
        	log.debug("PORK_LOG >>> 로그인 오류" + e.getMessage());
        	e.printStackTrace();
        }
        finally {
        	
        	response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
        
        log.info("PORK_LOG >>> userLogin END >>>");
        
    }
    
    /**
     * 
     * @param request
     * @return response
     * @throws Exception
     * @since  2016.04.03
     * @author thomas 
     * Description : 콘텐츠 등록처리
     */
    @RequestMapping(value="/api/writeContent.do", method = RequestMethod.POST)
    public void writeContent(HttpServletRequest  request, HttpServletResponse response) throws Exception {
    	
    	log.info("PORK_LOG >>> writeContent STRAT >>> ");
    	
    	MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	log.debug("PORK_LOG >>> writeContent Request param  \t:  " + multipartHttpServletRequest.getInputStream().toString());    	
    	        
        JsonObject json = new JsonObject();
        
        try {
        	
	        if(porkService.fileUpload(multipartHttpServletRequest)) {
	        	json.addProperty("result_mgs", "Contents Upload Success");
            	json.addProperty("result_code", "S0000");	        	
	    	}
	        else {
	        	json.addProperty("result_mgs", "Contents Upload Fail");
            	json.addProperty("result_code", "E0003");
	        }
        } catch (Exception e) {
        	
        	json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "E9999");
        	log.debug("PORK_LOG >>> 이미지 업로드 오류", e.getCause());
        	log.debug("PORK_LOG >>> 이미지 업로드 오류" + e.getMessage());
        	e.printStackTrace();
        }
        finally {
        	response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
        
        // 최종 결과 return
        
    	log.info("PORK_LOG >>> writeContent END >>> ");
    	
    }
    
    /**
     * 
     * @param request
     * @return response
     * @throws Exception
     * @since  2016.03.27
     * @author thomas
     * Description 사용자가 이미 등록되었는지 회원가입시 체크
     */
    @RequestMapping(value="/api/checkUser.do", method = RequestMethod.POST)
    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	log.info("PORK_LOG >>> checkUser STRAT >>> ");
    	    	
    	String param = null;
        param = util.getReqData(request);
        
        log.debug("PORK_LOG >>> checkUser Request param  \t:  " + param);
        
    	// API형대로 제공될 것이므로 JSON 타입으로 요청받은 정보를 서비스에 맞게 Map 데이터로 변경함
    	UserVO userVo = gson.fromJson(param, UserVO.class);
    	    	
    	// 처리 결과 리턴 값
    	JsonObject json = new JsonObject(); 
    	try {
    		
	        int ret = porkService.checkUser(userVo);
	        
	        if (ret == 0) {
	        	log.info("PORK_LOG >>> checkUser Response Param [" + ret + "]");
	        	json.addProperty("result_mgs", "success");
	        	json.addProperty("result_code", "S0000");
	        }
	        else {
	        	json.addProperty("result_mgs", "email address has already");
	        	json.addProperty("result_code", "E0004");
	        }
    	} 
    	catch (Exception e) {
    		
    		json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "E9999");
        	log.debug("PORK_LOG >>> 회원가입체크 오류", e.getCause());
        	log.debug("PORK_LOG >>> 회원가입체크 오류" + e.getMessage());
    		e.printStackTrace();
    	}
    	finally {
    		response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
    	}
        
        
        log.info("PORK_LOG >>> checkUser END >>>");
    }
    
    /**
     * 
     * @param request
     * @return response
     * @throws Exception
     * @since  2016.04.03
     * @author thomas
     * Description : 회원가입 데이터 처리
     */
    @RequestMapping(value="/api/joinUser.do", method = RequestMethod.POST)
    public void joinUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
    	log.info("PORK_LOG >>> joinUser STRAT >>> ");
    	
    	// Request 객체에서 JSON Parameter 추출
    	String param = null;
        param = util.getReqData(request);
        log.debug("PORK_LOG >>> joinUser Request param  \t:  " + param);
        
    	// API형대로 제공될 것이므로 JSON 타입으로 요청받은 정보를 서비스에 맞게 Map 데이터로 변경함
    	UserVO userVo = gson.fromJson(param, UserVO.class);
    	
        // 처리 결과 리턴 값
    	int joinResult = 0;
    	
    	JsonObject json = new JsonObject();
    	
    	try {
    		joinResult = porkService.joinUser(userVo);
    		
    		log.info("PORK_LOG >>> joinResult Param [" + joinResult + "]");
    		            
            if (joinResult == 1) {        	
            	json.addProperty("result_mgs", "success");
            	json.addProperty("result_code", "S0000");
            }
            else {
            	json.addProperty("result_mgs", "fail");
            	json.addProperty("result_code", "E0005");
            }
    	}
    	catch (Exception e) {
    		
    		json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "E9999");
        	log.debug("PORK_LOG >>> 회원가입 오류", e.getCause());
        	log.debug("PORK_LOG >>> 회원가입 오류" + e.getMessage());
    		e.printStackTrace();
    	}
    	finally {
    		response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
    	}
    	
        log.info("PORK_LOG >>> joinUser END >>>");
        
    }
    
    
}


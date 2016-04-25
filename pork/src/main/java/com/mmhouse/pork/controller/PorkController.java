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
     * @param commandMap
     * @return Map
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
        
        JsonObject json = null; 
        
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
                	json.addProperty("result_code", "0000");
                }
                else {
                	json = new JsonObject();
                	json.addProperty("result_mgs", "data not found");
                	json.addProperty("result_code", "9999");
                }
             }
             else {
             	json = new JsonObject();
             	json.addProperty("result_mgs", "로그인 정보가 부족합니다.");
             	json.addProperty("result_code", "9999");
             	
             }
             
        }
        catch (Exception e) {
        	e.printStackTrace();
        	
        	if (json == null) {
        		json = new JsonObject();
        	}
        	
        	json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "9999");
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
     * @param commandMap
     * @return Map
     * @throws Exception
     * @since  2016.04.03
     * @author thomas
     * @refer url : http://addio3305.tistory.com/79
     * Description : 콘텐츠 등록처리
     */
    @RequestMapping(value="/api/writeContent.do", method = RequestMethod.POST)
    public void writeContent(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	log.info("PORK_LOG >>> writeContent STRAT >>> ");
    	
    	String param = null;
        
        param = util.getReqData(request);
        
        log.debug("PORK_LOG >>> writeContent Request param  \t:  " + param);
        
        // Contents Data 추출
        ContentsVO contentsVo = gson.fromJson(param, ContentsVO.class);
        
        log.debug("PORK_LOG >>> writeContent contentsVo string  \t:  " + gson.toJson(contentsVo).toString());
        
        // Media Data 리스트 추출
        List<ContentsMediaVO> contMediaList = (List<ContentsMediaVO>)contentsVo.getContentsMedia();
        
        JsonObject json = null; 
        
        try {
        	        	
        	int wirteResult = 0;
        	Map<String,Object> retMap;
        	
        	// contents table에 데이터 등록
        	try {
        		wirteResult = porkService.writeContent(contentsVo);
        	}
        	catch (Exception e){
        		e.printStackTrace();
        	}
            
        	log.debug("PORK_LOG >>> writeContent wirteResult  \t:  " + wirteResult);
        	
        	if (wirteResult == 1) {
        		
        		// 등록된 table에서 contents id 조회
    	        retMap = porkService.getContId(contentsVo);        	
        		
        		if (contMediaList.size() > 0) {
        			
        			Iterator it = contMediaList.iterator();
            		
                	while(it.hasNext()) {
                		
                		ContentsMediaVO contentsMediaVo = (ContentsMediaVO)it.next();
                		contentsMediaVo.setContId(String.valueOf(retMap.get("contId")));
                		contentsMediaVo.setContType(contentsVo.getContType());
                		
                		// 조회된 content id로 contents_media 테이블 데이터 등록
            	        wirteResult = porkService.writeContentMedia(contentsMediaVo);

                	}
                	
                	json = new JsonObject();
                	json.addProperty("result_mgs", "success");
                	json.addProperty("result_code", "9999");
                	
        		}
        	}
        	else {
        		json = new JsonObject();
            	json.addProperty("result_mgs", "Contents Upload Fail");
            	json.addProperty("result_code", "9999");
        	}
        }
        catch (Exception e) {
        	e.printStackTrace();
        	
        	if (json == null) {
        		json = new JsonObject();
        	}
        	
        	json.addProperty("result_mgs", e.getMessage());
        	json.addProperty("result_code", "9999");
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
     * @param commandMap
     * @return Map
     * @throws Exception
     * @since  2016.03.27
     * @author thomas
     * @return 
     * @refer url : http://addio3305.tistory.com/79
     * Description 사용자가 이미 등록되었는지 회원가입시 체크
     */
    @RequestMapping(value="/api/checkUser.do", method = RequestMethod.POST)
    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	log.info("PORK_LOG >>> checkUser STRAT >>> ");
    	    	
    	String param = null;
        param = util.getReqData(request);
        
        log.debug("PORK_LOG >>> checkUser Request param  \t:  " + param);
        
    	// API형대로 제공될 것이므로 JSON 타입으로 요청받은 정보를 서비스에 맞게 Map 데이터로 변경함
    	JSONObject jObj = new JSONObject(param); // this parses the json
    	Iterator it = jObj.keys(); //gets all the keys
    	
    	Map<String, Object> commandMap = (Map<String, Object>)new HashMap();
    	
    	while(it.hasNext()) {
    		
    	    String key = String.valueOf(it.next()); // 키
    	    Object o = jObj.get(key);               // 키값 
    	    commandMap.put(key, o);                 // param으로 저장

    	} 
    	
    	// 처리 결과 리턴 값
        Map<String,Object> retMap = porkService.checkUser(commandMap);
        
        JsonObject json = null; 
        
        if (retMap != null) {
        	log.info("PORK_LOG >>> checkUser Response Param [" + retMap.toString() + "]");
        	json = new JsonParser().parse(retMap.toString()).getAsJsonObject();
        	log.info("PORK_LOG >>> checkUser Response json [" + json.toString() + "]");
        	json.addProperty("result_mgs", "success");
        	json.addProperty("result_code", "0000");
        }
        else {
        	json = new JsonObject();
        	json.addProperty("result_mgs", "data not found");
        	json.addProperty("result_code", "9999");
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        
        log.info("PORK_LOG >>> checkUser END >>>");
    }
    
    /**
     * 
     * @param commandMap
     * @return Map
     * @throws Exception
     * @since  2016.04.03
     * @author thomas
     * @refer url : http://addio3305.tistory.com/79
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
    	JSONObject jObj = new JSONObject(param); // this parses the json
    	Iterator it = jObj.keys(); //gets all the keys
    	
    	Map<String, Object> commandMap = (Map<String, Object>)new HashMap();
    	
    	while(it.hasNext()) {
    		
    	    String key = String.valueOf(it.next()); // 키
    	    Object o = jObj.get(key);               // 키값 
    	    commandMap.put(key, o);                 // param으로 저장

    	}
    	
        // 처리 결과 리턴 값
    	int joinResult = 0;
    	
    	try {
    		joinResult = porkService.joinUser(commandMap);
    	}
    	catch (Exception e) {
    		log.info("PORK_LOG >>> joinResult Exception [" + e.getMessage() + "]");
    		e.printStackTrace();
    	}
    	
    	log.info("PORK_LOG >>> joinResult Param [" + joinResult + "]");
    	
    	// Map Data를 JSON 값으로 치환
    	JSONObject json = new JSONObject();
        
        if (joinResult == 1) {        	
        	json.put("result_mgs", "success");
        	json.put("result_code", "0000");
        }
        else {
        	json.put("result_mgs", "fail");
        	json.put("result_code", "9999");
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        
        log.info("PORK_LOG >>> joinUser END >>>");
        
    }
    
    
}


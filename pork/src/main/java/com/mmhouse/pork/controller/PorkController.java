package com.mmhouse.pork.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mmhouse.pork.service.PorkService;
import com.mmhouse.pork.vo.CommandMap;
import com.mmhouse.pork.vo.userVO;

@Controller
public class PorkController {
	
	Logger log = Logger.getLogger(this.getClass());
    
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
    	
    	// Request 객체에서 JSON Parameter 추출
    	String param = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
 
        param = stringBuilder.toString();
        
        log.debug("PORK_LOG >>> userLogin Request param  \t:  " + param);
        
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
        Map<String,Object> userInfo = porkService.userLogin(commandMap);
        
        //Gson gson = new Gson();
        JsonObject json = null; 
        
        if (userInfo != null) {
        	log.info("PORK_LOG >>> userLogin Response Param [" + userInfo.toString() + "]");
        	
        	// Map Data를 JSON 값으로 치환
        	//String mapString = gson.toJson(userInfo.toString());
        	//log.info("PORK_LOG >>> userLogin Response mapString [" + mapString + "]");
        	
        	json = new JsonParser().parse(userInfo.toString()).getAsJsonObject();
        	
        	//json = (JsonObject) new JsonParser().parse(mapString);
        	log.info("PORK_LOG >>> userLogin Response json [" + json.toString() + "]");
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
        
        log.info("PORK_LOG >>> userLogin END >>>");
        
    }
    
    /**
     * 
     * @param commandMap
     * @return Map
     * @throws Exception
     * @since  2016.03.27
     * @author thomas
     * @refer url : http://addio3305.tistory.com/79
     * Description 사용자가 이미 등록되었는지 회원가입시 체크
     */
    @RequestMapping(value="/api/checkUser.do", method = RequestMethod.POST)
    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
    	log.info("PORK_LOG >>> checkUser STRAT >>> ");
    	
    	// Request 객체에서 JSON Parameter 추출
    	String param = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
 
        param = stringBuilder.toString();
        
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
        Map<String,Object> userInfo = porkService.checkUser(commandMap);
        
        if (userInfo != null) {
        	log.info("PORK_LOG >>> checkUser Response Param [" + userInfo.toString() + "]");
        	
        	// Map Data를 JSON 값으로 치환
        	JSONObject json = new JSONObject(userInfo.toString());
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            
        }
        
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
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
 
        param = stringBuilder.toString();
        
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


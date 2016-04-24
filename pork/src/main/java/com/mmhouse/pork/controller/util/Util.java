package com.mmhouse.pork.controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class Util {
	
	/**
     * HttpServletRequest에서 받은 Json Data 추출
     * @return
     */
    public String getReqData(HttpServletRequest request) throws Exception {
    	
    	// Request 객체에서 JSON Parameter 추출
    	String param = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
            	
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer strBuf = new StringBuffer(); 
                
                while((line = bufferedReader.readLine()) != null) {
                	strBuf.append(line);
               	}
                
                param = strBuf.toString();
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
        
        return param;
        
    }
    
}

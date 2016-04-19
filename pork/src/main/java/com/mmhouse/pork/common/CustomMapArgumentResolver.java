package com.mmhouse.pork.common;
 
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
 


import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
 


import com.mmhouse.pork.vo.CommandMap;
 
public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver{
	
	Logger log = Logger.getLogger(this.getClass());
	
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
    	log.debug("PORK_LOG [CustomMapArgumentResolver] >>> supportsParameter " + CommandMap.class.isAssignableFrom(parameter.getParameterType()));
        return CommandMap.class.isAssignableFrom(parameter.getParameterType());
    }
 
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CommandMap commandMap = new CommandMap();
         
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Enumeration<?> enumeration = request.getParameterNames();
        
        log.debug("PORK_LOG [CustomMapArgumentResolver] >>> ParameterNames " + request.getParameterNames());
        
        String key = null;
        String[] values = null;
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            log.debug("PORK_LOG [CustomMapArgumentResolver] >>> key " + key);
            values = request.getParameterValues(key);
            if(values != null){
                commandMap.put(key, (values.length > 1) ? values:values[0] );
            }
        }
        return commandMap;
    }
}
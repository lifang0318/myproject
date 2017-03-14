package com.rareboom.member.util;

import java.util.HashMap;  
import java.util.Map;  
import java.util.Properties;  
import org.springframework.beans.BeansException;  
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;  
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;  

/**
 * 用于暴露Spring中加载的properties文件的工具类
 * 集成自spring的PropertyPlaceholderConfigurer
 * @author frankx
 *
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {  
  
    private static Map<String, Object> ctxPropertiesMap;  
  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        ctxPropertiesMap = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    //static method for accessing context properties  
    public static String getContextProperty(String name) {  
        return ctxPropertiesMap.get(name).toString();  
    }  
} 
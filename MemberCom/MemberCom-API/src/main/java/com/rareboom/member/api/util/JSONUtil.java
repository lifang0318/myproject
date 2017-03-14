package com.rareboom.member.api.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
/**
 * JSON工具类 依赖fastjson
 * @author wudi
 *
 */
public class JSONUtil {
	
	public static String modelToJSON(Object arg0){
		return JSON.toJSONString(arg0);
	}
	
	public static String jSOnToString(String str){
		return (String) JSON.parse(str);
	}
	
	public static <T> T jSONToModel(String str,Class<T> clazz){
		return JSON.parseObject(str, clazz);
	}
	
	public static <T> List<T> jSONToArryModel(String str,Class<T> clazz){
		return JSON.parseArray(str, clazz);
	}
	

}

package com.rareboom.member.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPassword {
	/**
	 * 验证密码格式是否正确
	 * @param password
	 * @return
	 */
    public static boolean isValidPassword(String password){
    	Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
    	Matcher m = p.matcher(password);
    	System.out.println(m.matches());
    	return m.matches();
    }
}

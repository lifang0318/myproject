package com.rareboom.member.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidMobile {
	/**
	 * 验证手机号格式是否正确
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches());
		return m.matches();
	}
}
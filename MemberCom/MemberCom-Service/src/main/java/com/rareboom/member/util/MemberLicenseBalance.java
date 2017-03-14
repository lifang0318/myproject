package com.rareboom.member.util;

import java.util.HashMap;
import java.util.Map;

public class MemberLicenseBalance {
    Map<String,Integer> licenses=new HashMap<String,Integer>();

	public Map<String, Integer> getLicenses() {
		licenses.put("AA", 20);
		licenses.put("BB", 30);
		return licenses;
	}

	public void setLicenses(Map<String, Integer> licenses) {
		this.licenses = licenses;
	}
   
}

package com.rareboom.member.scheme;

import org.hibernate.validator.constraints.NotEmpty;

public class UploadHeadImageScheme extends BaseScheme {

	private static final long serialVersionUID = 1L;
    @NotEmpty(message="{uploadHeadImage.memberId.null}")
	private String memberId;
    @NotEmpty(message="{uploadHeadImage.base64.null}")
	private String base64;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

}

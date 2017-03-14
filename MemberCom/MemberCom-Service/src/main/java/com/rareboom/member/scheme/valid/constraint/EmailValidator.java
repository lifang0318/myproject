package com.rareboom.member.scheme.valid.constraint;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.scheme.FindMemberDetailScheme;
import com.rareboom.member.scheme.valid.IsExistEmail;
import com.rareboom.member.service.IMemberFindService;

public class EmailValidator implements ConstraintValidator<IsExistEmail, String> {

	@Autowired
	private IMemberFindService memberFindService;

	@Override
	public void initialize(IsExistEmail constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		FindMemberDetailScheme scheme = new FindMemberDetailScheme();
		try {
			List<MemberDetail> details = memberFindService.findMemberDetailList(scheme);
			if (null != details && !details.isEmpty()) {
				for (MemberDetail detail : details) {
					if (value.trim().equals(detail.getMemberMail())) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}

package com.rareboom.member.service.impl;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.rareboom.member.service.lang.ParameterException;

/**
 * scheme验证工具
 */
@Service
public class ValidateService {

	@Autowired
	private LocalValidatorFactoryBean validator;

	public <T> void validate(T t) throws Exception {
		Set<ConstraintViolation<T>> validators = validator.validate(t);
		for (ConstraintViolation<T> constraintViolation : validators) {
			throw new ParameterException(constraintViolation.getMessage());
		}
	}
}

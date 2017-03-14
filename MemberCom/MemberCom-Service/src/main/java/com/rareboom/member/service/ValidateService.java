package com.rareboom.member.service;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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
			throw new IllegalArgumentException(constraintViolation.getMessage());
		}
	}
}

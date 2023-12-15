package com.electro.hub.validate;



import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {
     
	private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//Write logic here
		
		logger.info("message from isValid:",value);
		
		if(value.isBlank()) {
			return false;
		}else {
			return true;
		}
		
		
		
		
		
	}

	
	
}

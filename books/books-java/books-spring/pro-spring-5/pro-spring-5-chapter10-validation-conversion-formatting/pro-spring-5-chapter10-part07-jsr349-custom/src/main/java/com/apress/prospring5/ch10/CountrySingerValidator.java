package com.apress.prospring5.ch10;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.apress.prospring5.ch10.obj.Singer;

public class CountrySingerValidator implements ConstraintValidator<CheckCountrySinger, Singer> {

    @Override
    public void initialize(CheckCountrySinger constraintAnnotation) {
    }

    @Override
    public boolean isValid(Singer singer, ConstraintValidatorContext context) {
	boolean result = true;
	if (singer.getGenre() != null && (singer.isCountrySinger() && (singer.getLastName() == null || singer.getGender() == null))) {
	    result = false;
	}
	return result;
    }
}

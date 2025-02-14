package com.idat.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.idat.dto.CategoryDto;
import com.idat.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();
		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/Json shouldn`tbe null or empty");
		}else {
			//validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is emprty or null");
			}else {
				if (categoryDto.getName().length()<10) {
					error.put("name", "name lenght min 10");
				}if (categoryDto.getName().length()>100) {
					error.put("name", "name lenght max 100");
				}
			}
			//validation description
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is emprty or null");
			}
			//validation isActive
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "isActive field is emprty or null");
			}else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() &&
						categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "invalid value isActive field");
				}
			}
			
		}
		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
		
	}
	
}

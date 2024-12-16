package com.idat.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.idat.dto.CategoryDto;
import com.idat.dto.CategoryResponse;
import com.idat.entity.Category;
import com.idat.repository.CategoryRepository;
import com.idat.service.*;

@Service
public class CategoryServiceImpl implements  CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		Category category = mapper.map(categoryDto, Category.class);
		category.setIsDeleted(false); 
		category.setCreatedBy(1); 
		Category saveCategory= categoryRepository.save(category); 
		  if (ObjectUtils.isEmpty(saveCategory)) {
			  return false; 
			  }
		  	return true;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategories() {
		
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> categoryList = categories.stream().map(cat-> mapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryList;
	}

}

package com.idat.service;

import java.util.List;

import com.idat.dto.CategoryDto;
import com.idat.dto.CategoryResponse;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategories();

	public List<CategoryResponse> getActiveCategories();

	public CategoryDto getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategory(Integer id);

}

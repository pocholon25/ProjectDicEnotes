package com.idat.service;

import java.util.List;

import com.idat.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategories();

}

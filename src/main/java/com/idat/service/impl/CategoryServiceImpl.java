package com.idat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.idat.dto.CategoryDto;
import com.idat.dto.CategoryResponse;
import com.idat.entity.Category;
import com.idat.exception.ResourceNotFounfException;
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
		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false); 
			category.setCreatedBy(1); 
		}else {
			updateCategory(category);
		}	
		Category saveCategory= categoryRepository.save(category); 
		  if (ObjectUtils.isEmpty(saveCategory)) {
			  return false; 
			  }
		  	return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepository.findById(category.getId());
		if (findById.isPresent()) {
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
		}
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategories() {	
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat-> mapper.map(cat, CategoryResponse.class)).toList();	
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(()-> new ResourceNotFounfException("Category not found with id: "+id));
		if (!ObjectUtils.isEmpty(category)) {
			category.getName().toUpperCase();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCategory = categoryRepository.findById(id);
		if (findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}
		return false;
	}

}

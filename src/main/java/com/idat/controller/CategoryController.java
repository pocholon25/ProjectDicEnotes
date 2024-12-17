package com.idat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.dto.CategoryDto;
import com.idat.dto.CategoryResponse;
import com.idat.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){		
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){	
		List<CategoryDto> allCategories = categoryService.getAllCategories();
		if (CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		}else {
			return new ResponseEntity<>(allCategories,HttpStatus.OK);
		}
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory(){		
		List<CategoryResponse> allCategories = categoryService.getActiveCategories();
		if (CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		}else {
			return new ResponseEntity<>(allCategories,HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id){
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("category not found with Id = "+id, HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
		Boolean deleted = categoryService.deleteCategory(id);
		if (deleted) {
			return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
		}
		return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
	
	
}

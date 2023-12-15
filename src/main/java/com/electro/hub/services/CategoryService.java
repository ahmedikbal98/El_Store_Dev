package com.electro.hub.services;


import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.CategoryDto;
import com.electro.hub.dtos.PageableResponse;


 public interface CategoryService {
	

		//create 
		public CategoryDto createCategory(CategoryDto catDto);
		
		
		
		//read
		
		 public CategoryDto getSingleCategoryDto(String categoryId);
		 
		 public PageableResponse<CategoryDto> getAllCategoryDto (
				 int pageNumber, int pageSize, String sortBy, String sortDir
				 );
		//update
		 
		 public CategoryDto updateCategory(CategoryDto catDto, String categoryId);
		
		//delete
		 
		 public ApiResponseMessage deleteCategory(String categoryId);
	
	
	

}

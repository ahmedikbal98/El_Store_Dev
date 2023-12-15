package com.electro.hub.services.impl;



import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.electro.hub.customexceptions.ResourceNotFoundException;
import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.CategoryDto;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;
import com.electro.hub.entities.Category;
import com.electro.hub.entities.User;
import com.electro.hub.helper.Helper;
import com.electro.hub.repositories.CategoryRepo;
import com.electro.hub.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto catDto) {
		
		String categoryId = UUID.randomUUID().toString();
		catDto.setCategoryId(categoryId);
		
		Category catToBeReturned =categoryRepo.save(modelMapper.map(catDto, Category.class));
		return modelMapper.map(catToBeReturned,  CategoryDto.class);
		
	}

	@Override
	public CategoryDto getSingleCategoryDto(String categoryId) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(
				
				()->new ResourceNotFoundException("Category not found with given ID")
				);
		
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategoryDto(int pageNumber, int pageSize, String sortBy, String sortDir) {
		//pagenumber default start from 0
				Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
				
				Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
				
				Page<Category> page = categoryRepo.findAll(pageable);
				//List<Category> pages = page.getContent();
				
				return Helper.getPageableResponse(page, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catDto, String categoryId) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException()
				);
		category.setTitle(catDto.getTitle());
		category.setCoverImage(catDto.getCoverImage());
		
		
		
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public ApiResponseMessage deleteCategory(String categoryId) {
		Category categoryToBeDeleted = categoryRepo.findById(categoryId).
				orElseThrow(
						
						
						()-> new ResourceNotFoundException("No Category presentwith this userID")
						);
		
		categoryRepo.delete(categoryToBeDeleted);
		
		ApiResponseMessage response = ApiResponseMessage.builder().status(HttpStatus.OK).success(true).message("Category deleted successfully").build();
		
		
		
		return response;
	}
	
	
	//create
	
	
	
    //Read
	
	//update
	
	
	//delete

}

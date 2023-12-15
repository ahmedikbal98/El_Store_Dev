package com.electro.hub.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.CategoryDto;
import com.electro.hub.dtos.ImageResponse;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;
import com.electro.hub.services.CategoryService;
import com.electro.hub.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FileService fileService;
	
	
	@Value("${category.profile.image.path}")
	private String categoryImageUploadPath;
	
	//logger configuration
	
	private Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	
	//create
	@PostMapping("")
	public ResponseEntity<CategoryDto> createCategory (@RequestBody CategoryDto catDto){
		return new ResponseEntity<CategoryDto>( categoryService.createCategory(catDto), HttpStatus.CREATED);
	    
		
	}
	
	//read
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory (@PathVariable String categoryId){
		
		CategoryDto category = categoryService.getSingleCategoryDto(categoryId);
		
		return new  ResponseEntity<CategoryDto>(category, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC", required = false) String sortDir
			){
		PageableResponse<CategoryDto> categories = categoryService.getAllCategoryDto(pageNumber, pageSize, sortBy, sortDir);
			
 		return new ResponseEntity<PageableResponse<CategoryDto>>(categories, HttpStatus.OK);
	}
	
	//Update 
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId ){
		
		return new ResponseEntity<CategoryDto>(
				categoryService.updateCategory(categoryDto, categoryId),
				HttpStatus.OK
				);
				
				
	}
	
	@DeleteMapping("/delete/{categoryId}")
	ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
		
		
		return new ResponseEntity<ApiResponseMessage>(
				categoryService.deleteCategory(categoryId),
				HttpStatus.OK
				);		
				
	}
	
	
	
	//upload user image
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("categoryImage") MultipartFile categoryImage, @PathVariable String categoryId  ) throws IOException{
		//uploading image and gettingimageName
		String imageName = fileService.uploadFile(categoryImage, categoryImageUploadPath);
		//getting userDto and setting imageName
		CategoryDto categoryDto =  categoryService.getSingleCategoryDto(categoryId);
		categoryDto.setCoverImage(imageName);
        categoryService.updateCategory(categoryDto, categoryId);
		
		
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName)
				.message("Image uploaded successfully").success(true).status(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
	}
	
	//fetch user image
	@GetMapping("/image/{categoryId}")
public void serveImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
		CategoryDto categoryDto= categoryService.getSingleCategoryDto(categoryId);
		logger.info("userImageName is : {}",categoryDto.getCoverImage());
		String imageName = categoryDto.getCoverImage();
		InputStream resource=  fileService.getResource(categoryImageUploadPath, imageName);
	   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
	
	

}

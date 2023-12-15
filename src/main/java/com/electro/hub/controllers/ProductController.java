package com.electro.hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.ProductDto;
import com.electro.hub.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
		
		return new ResponseEntity<>( productService.getSingleProduct(productId),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/all")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC", required = false) String sortDir
			){
		return new ResponseEntity<PageableResponse<ProductDto>>(
				productService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
				
	}
	
	
	@PostMapping("")
	public ResponseEntity<ProductDto> creatNewProduct (@RequestBody ProductDto productDto){
		return new ResponseEntity<ProductDto>(productService.createNewProduct(productDto),HttpStatus.OK);
	}
	
	
   @PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct (@RequestBody ProductDto productDto, @PathVariable String productId){
		return new ResponseEntity<ProductDto>(productService.updateProduct(productDto,productId), HttpStatus.OK);
	}
	
   @DeleteMapping("/{productId}")
   public ApiResponseMessage deleteProduct(@PathVariable String productId) {
	   return productService.deleteProduct(productId);
   }
   
   //searching
   
   @GetMapping("/live")
   public ResponseEntity<PageableResponse<ProductDto>> getLiveProducts(
		   @RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC", required = false) String sortDir
		   ){
	   
	   return new ResponseEntity<PageableResponse<ProductDto>>(
			   productService.gettLive(pageNumber, pageSize, sortBy, sortDir),
			   HttpStatus.OK
			   );
   }
   
   @GetMapping("/search/{subTitle}")
   public ResponseEntity<PageableResponse<ProductDto>> getProductBySubTitle(
		   @PathVariable String subTitle,
		   @RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC", required = false) String sortDir
		   ){
	   return new ResponseEntity<PageableResponse<ProductDto>>(
			   productService.searchBySubTitile(subTitle, pageNumber, pageSize, sortBy, sortDir),
			   HttpStatus.OK
			   );
			   
	   
   }
   
   
   
   

}

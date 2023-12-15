package com.electro.hub.services;

import java.util.List;

import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.ProductDto;

public interface ProductService {
	
	//create
	
	public ProductDto createNewProduct(ProductDto productDto);
	
	//read
	//single product
	
	public ProductDto getSingleProduct (String productId);
	
	//get all products 
	
	public PageableResponse<ProductDto> getAll(
			int pageNumber, int PageSize, String sortBy, String sortDir
			);
	
	//get All live
	
	public PageableResponse<ProductDto> gettLive(
			int pageNumber, int PageSize, String sortBy, String sortDir
			);
	
	//update
	
	public ProductDto updateProduct(ProductDto productDto, String productId);
	
	//delete
	
	public ApiResponseMessage deleteProduct(String productId);
	
	//search
	
	//search by  title
	
	public PageableResponse<ProductDto> searchBySubTitile(String subTitle,
			
			int pageNumber, int PageSize, String sortBy, String sortDir
			);
	
	

}

package com.electro.hub.services.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.ProductDto;
import com.electro.hub.entities.Product;
import com.electro.hub.helper.Helper;
import com.electro.hub.repositories.ProductRepo;
import com.electro.hub.services.ProductService;


@Service
public class ProductServiceImpl implements ProductService {
    
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ModelMapper mapper;
	
	//Create
	@Override
	public ProductDto createNewProduct(ProductDto productDto) {
		
		String productId = UUID.randomUUID().toString();
		
		productDto.setProductId(productId);
		productDto.setAddedDate(new Date());
		
		
		Product productToBeReturned = productRepo.save(mapper.map(productDto, Product.class));
	     return mapper.map(productToBeReturned,ProductDto.class);
	
	}

	@Override
	public ProductDto getSingleProduct(String productId) {
		
		Product product = productRepo.findById(productId).
				orElseThrow(()->
				        new ResourceNotFoundException("Product not found with given id")
				
						);
		
		
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int PageSize, String sortBy, String sortDir) {
		
	Sort sort = (sortDir.equalsIgnoreCase("desc"))?
			(Sort.by(sortBy).descending()):
				(Sort.by(sortBy).ascending());
	
	 Pageable pageable = PageRequest.of(pageNumber, PageSize,sort);
		
	 Page<Product> page =  productRepo.findAll(pageable);
	 
	 return Helper.getPageableResponse(page, ProductDto.class);
	 
	 
	}

	@Override
	public PageableResponse<ProductDto> gettLive(
			int pageNumber, int PageSize, String sortBy, String sortDir
			) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?
				(Sort.by(sortBy).descending()):
					(Sort.by(sortBy).ascending());
		
		 Pageable pageable = PageRequest.of(pageNumber, PageSize,sort);
			
		 Page<Product> page =  productRepo.findByLiveTrue(pageable);
		 
		 return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {
		
		Product product = productRepo.findById(productId).
				orElseThrow(
						
						()-> new ResourceNotFoundException("Product not found with given id")
						);
	    product.setAddedDate(productDto.getAddedDate());
	    product.setDescription(productDto.getDescription());
	    product.setDiscountedPrice(productDto.getDiscountedPrice());
	    product.setLive(productDto.isLive());
	    product.setPrice(productDto.getPrice());
	    product.setQuantity(productDto.getQuantity());
	    product.setStock(productDto.isStock());
	    product.setTitle(productDto.getTitle());
	    
	    productRepo.save(product);
	    
	    return mapper.map(product, ProductDto.class);
		
	}

	@Override
	public ApiResponseMessage deleteProduct(String productId) {
		
		Product product = productRepo.findById(productId).
				orElseThrow(
						
						()-> new ResourceNotFoundException("Product not found with given id")
						);
		
		productRepo.delete(product);
		return new ApiResponseMessage().builder().success(true)
				.message("Product deleted successfully").status(HttpStatus.OK).build();
	}

	@Override
	public PageableResponse<ProductDto> searchBySubTitile(String subTitle,
			int pageNumber, int PageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc"))?
				(Sort.by(sortBy).descending()):
					(Sort.by(sortBy).ascending());
		
		 Pageable pageable = PageRequest.of(pageNumber, PageSize,sort);
			
		 Page<Product> page =  productRepo.findByTitleContaining(subTitle,pageable);
		 
		 return Helper.getPageableResponse(page, ProductDto.class);
	
	}

}

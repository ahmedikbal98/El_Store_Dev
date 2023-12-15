package com.electro.hub.customexceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.electro.hub.dtos.ApiResponseMessage;

import io.swagger.annotations.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 
	//Resource not found exception Handler
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex){
	 
		ApiResponseMessage response =ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
	    logger.info("Exception handler invoked");
		return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}
	
	
	//Method argument not valid exception handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> allErrors= ex.getBindingResult().getAllErrors();
		Map<String,Object> response = new HashMap<>();
		
				//building the respnose map
				
				allErrors.stream().forEach(objectError->{
					String message = objectError.getDefaultMessage();
					String field = ((FieldError) objectError).getField();
					 response.put(field, message);
				});
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	
	//File extension not valid exception
	
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex){
		ApiResponseMessage response = new ApiResponseMessage();
		response.setMessage(ex.getMessage());
		response.setSuccess(true);
		response.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	 
    
}

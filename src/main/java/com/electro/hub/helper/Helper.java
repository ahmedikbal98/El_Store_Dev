package com.electro.hub.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;
import com.electro.hub.entities.User;

public class Helper {
	
	//method to get Pageable response object with the help of provided page object and DtoClass
	//U is entity and V is Dto
	public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
		
   List<U> entities = page.getContent();
	List<V> dtoList = entities.stream().
			map(
					object->new ModelMapper().map(object, type)
				).
			collect(Collectors.toList());
	PageableResponse<V> response = new PageableResponse<V>();
	response.setContent(dtoList);
	response.setPageNumber(page.getNumber());
	response.setPageSize(page.getSize()); 
	response.setTotalElements(page.getTotalElements());
	response.setTotalPages(page.getTotalPages());
	response.setLastPage(page.isLast());
	
	return  response;
	}

}

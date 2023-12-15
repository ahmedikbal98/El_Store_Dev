package com.electro.hub.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryDto {
	
   
	private String categoryId;
	
	@NotBlank(message = "Description required")
	@Min(value=4, message = "Title must be of minimum 4 characters !!")
	private String title;
	
	@NotBlank
	private String description;
	
	private String coverImage;
	
	

}

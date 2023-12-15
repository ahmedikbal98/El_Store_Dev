package com.electro.hub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
	@Id
	@Column(name="id")
	private String categoryId;
	
	@Column(name = "category_title", length = 60)
	private String title;
	
	@Column(name = "category_desc", length =500)
	private String description;
	
	private String coverImage;
	
	
	
	
	

	

}

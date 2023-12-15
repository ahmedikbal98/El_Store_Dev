package com.electro.hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electro.hub.entities.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {

	
	
}

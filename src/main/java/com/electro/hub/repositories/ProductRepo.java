package com.electro.hub.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.electro.hub.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String>{
	
	Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);

}

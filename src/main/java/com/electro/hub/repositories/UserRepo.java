package com.electro.hub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electro.hub.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	
	Optional<List<User>> findByNameContaining(String keyword);
	
	Optional<User> findByEmailAndPassword(String email, String password);

}

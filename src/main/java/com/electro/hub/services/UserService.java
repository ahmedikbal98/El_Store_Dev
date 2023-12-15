package com.electro.hub.services;

import java.util.List;

import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;


public interface UserService {
	
	//CREATE
	
	public UserDto createUser (UserDto user);
	
	
	//READ
	
	//get single user by id
    UserDto getUserById(String userId);
	//get single user by email
    UserDto getUserByEmail(String email);
    
    //get all users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int PageSize, String sortBy, String sortDir);
	
	//UPDATE
	public UserDto updateUser (UserDto user,String userId);
	
	
	//DELETE
	public void deleteUser(String userId);

    //SEARCH USER
	
	List<UserDto> searchUser(String keyword);

}

package com.electro.hub.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ContentVersionStrategy;

import com.electro.hub.customexceptions.ResourceNotFoundException;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;
import com.electro.hub.entities.User;
import com.electro.hub.helper.Helper;
import com.electro.hub.repositories.UserRepo;
import com.electro.hub.services.UserService;

@Service
public class UserServiceImpl implements UserService  {
    
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String userImageUploadPath;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	//Create user
	@Override
	public UserDto createUser(UserDto userDto) {
		//generating and setting random unique userID
	   String userId = UUID.randomUUID().toString();
	   userDto.setUserId(userId);
	   //converting userDto object into User object to persist into the DB 
	   User convertedUser = mapper.map(userDto, User.class);
	    //System.out.println(convertedUser.toString());
	   //create a new user Record into db
       userRepo.save(convertedUser);
       
       //Returning UserDto object to caller class // ex: controller
      // System.out.println(mapper.map(convertedUser, UserDto.class).toString());
       return mapper.map(convertedUser, UserDto.class);
	}
    
	//Get a single User by ID
	@Override
	public UserDto getUserById(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with given ID"));
		return mapper.map(user, UserDto.class);
	}
    
	//Get a single User by email
	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepo.findByEmail(email).
				orElseThrow(()-> new ResourceNotFoundException("User not found with the given email id and password"));
		return mapper.map(user, UserDto.class);
	}

	//get all users
	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		//pagenumber default start from 0
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<User> page = userRepo.findAll(pageable);
		List<User> users = page.getContent();
		
		return Helper.getPageableResponse(page, UserDto.class);
	}
;
	//Update user
	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User userToBeUpdated = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		
		userToBeUpdated.setName(userDto.getName());
		userToBeUpdated.setAbout(userDto.getAbout());
		userToBeUpdated.setPassword(userDto.getPassword());
		userToBeUpdated.setGender(userDto.getGender());
		userToBeUpdated.setImageName(userDto.getImageName());
		
		
		return mapper.map(userRepo.save(userToBeUpdated),UserDto.class);
	}

	
	//Delete  a user
	@Override
	public void deleteUser(String userId) {
		User userToBeDeleted = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		
		 //delete user profile image
        //images/user/abc.png
        String fullPath = userImageUploadPath+ userToBeDeleted.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            logger.info("User image not found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		userRepo.delete(userToBeDeleted);
	}
	
	//Search Users

	@Override
	public List<UserDto> searchUser(String keyword) {
	
		List<User> users = userRepo.findByNameContaining(keyword).
				orElseThrow(()->new RuntimeException("No such users found"));
		List<UserDto> usersDto = users.stream().map(
				u->mapper.map(u, UserDto.class)).collect(Collectors.toList());
		return usersDto;
				
		
		
	}

}

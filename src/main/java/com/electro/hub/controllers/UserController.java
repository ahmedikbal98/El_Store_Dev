package com.electro.hub.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;
import org.slf4j.LoggerFactory;

import org.hibernate.mapping.UserDefinedType;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electro.hub.dtos.ApiResponseMessage;
import com.electro.hub.dtos.ImageResponse;
import com.electro.hub.dtos.PageableResponse;
import com.electro.hub.dtos.UserDto;
import com.electro.hub.services.FileService;
import com.electro.hub.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String userImageUploadPath;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//getting a single user
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser( @PathVariable String id){
		return new ResponseEntity<UserDto>(userService.getUserById(id), HttpStatus.OK);
	}
	
	// a singke user by email]
	
	@GetMapping("/email/{emailId}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId){
		return new ResponseEntity<UserDto>(userService.getUserByEmail(emailId),HttpStatus.OK);
	}
	
	//Getting All Users
	@GetMapping("/all")
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers (
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = "ASC", required = false) String sortDir
			){
		PageableResponse<UserDto> users = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PageableResponse<UserDto>>(users,HttpStatus.OK);
				
	}
	
	//Creating a new User
	
	@PostMapping
    @ApiOperation(value = "create new user !!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "not authorized !!"),
            @ApiResponse(code = 201, message = "new user created !!")
    })
	public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDto user){
		return new ResponseEntity<UserDto>(userService.createUser(user), HttpStatus.CREATED);
	}
	
	//Update an User
	
	@PutMapping("/{id}")
    @ApiOperation(value = "Update an existig user !!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "not authorized !!")
           
    })
	public ResponseEntity<UserDto> updateUser( @RequestBody UserDto user , @RequestParam String id ){
		return new ResponseEntity<UserDto>(userService.updateUser(user, id), HttpStatus.OK);
	}
	
	//Delete an user
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String id){
		userService.deleteUser(id);
		
		ApiResponseMessage message=  ApiResponseMessage.builder().message("User deleted with the given id").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponseMessage>(message,HttpStatus.OK);
	}
	
	//Search user with name keyword
	
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keyword){
		
		return new ResponseEntity<List<UserDto>>(userService.searchUser(keyword),HttpStatus.OK);
	}
	
	
	//upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile userImage, @PathVariable String userId  ) throws IOException{
		//uploading image and gettingimageName
		String imageName = fileService.uploadFile(userImage, userImageUploadPath);
		//getting userDto and setting imageName
		UserDto userDto =  userService.getUserById(userId);
		userDto.setImageName(imageName);
		userService.updateUser(userDto, userId);
		
		
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName)
				.message("Image uploaded successfully").success(true).status(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
	}
	
	//fetch user image
	@GetMapping("/image/{userId}")
public void serveImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		UserDto userDto= userService.getUserById(userId);
		logger.info("userImageName is : {}",userDto.getImageName());
		String imageName = userDto.getImageName();
		InputStream resource=  fileService.getResource(userImageUploadPath, imageName);
	   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
	
	

}

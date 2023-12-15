package com.electro.hub.dtos;

import com.electro.hub.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserDto {
	
    private String userId;
    
    
  
   @Size(min=3, max=20, message="Name should be of length between 3 to 20 charecters")  
    private String name;

    //@Email(message = "Invalid email")
   @Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message="Write a valid email")
    @NotBlank(message="Invalid user email !!")
    private String email;

    @NotBlank(message="Password is required")
    private String password;
    
    @Size(min=4, max =6, message ="Invalid gender")
    private String gender;

    @NotBlank(message="Write something about yourself!")
    private String about;

    @ImageNameValid
    private String imageName;


	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", gender=" + gender + ", about=" + about + ", imageName=" + imageName + "]";
	}

	

}

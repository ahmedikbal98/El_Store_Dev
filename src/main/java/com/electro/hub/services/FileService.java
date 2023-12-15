package com.electro.hub.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

//generic service to upload all kindof photos. ex: user prpfile, product image etc.
public interface FileService {
	
	 String uploadFile(MultipartFile file, String path) throws IOException;
	 
	 InputStream getResource(String path, String fileName) throws FileNotFoundException;

}

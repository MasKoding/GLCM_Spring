package com.gpch.login.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	@RequestMapping(value="upload",method=RequestMethod.GET)
	public String formUpload(Model model) {
		return "admin/upload";
	}
	@RequestMapping(value="file-upload",method=RequestMethod.POST)
	public ResponseEntity<String> actionUpload(Model model,MultipartFile file) throws IOException {
		String UPLOAD_DIR = "D:cv";
		Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());

	        // save the file to `UPLOAD_DIR`
	        // make sure you have permission to write
	        Files.write(path, file.getBytes());
	        return new ResponseEntity<>("File uploaded!!", HttpStatus.OK);
		
	}
}

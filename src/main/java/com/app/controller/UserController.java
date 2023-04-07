package com.app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.User;
import com.app.exception.ResourceNotFoundException;
import com.app.exception.UserNotFoundException;
import com.app.service.IService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	IService iservice;
	
	@PostMapping("/adduser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		User newUser = iservice.addUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User request) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(iservice.fetchUserByEmailIdAndPassword(request));
	}
	
	// send reset email and put reset token into DB
	@PostMapping("/forgotpass/{email}")
	public ResponseEntity<?> processForgotPassword(@PathVariable String email){
		User user = iservice.findByEmail(email);
		if (user != null) {
			String token = UUID.randomUUID().toString();
			iservice.createPasswordResetToken(token, user);
			String resetPasswordUrl = "http://localhost:3000/resetpassword?token=" + token;
			iservice.sendREsetPasswordEmail(user.getEmailId(), resetPasswordUrl);
			 }
		else {
			throw new UserNotFoundException("Could not find any customer with the email " + email);
			 }	
		return ResponseEntity.ok("Email sent successfully");
	}
	
	// (Authentication) match reset token from email link with the token in DB
	@GetMapping("/reset_password")
	public ResponseEntity showResetPasswordForm(@Param(value = "token") String token) {
		System.out.println("token "+token);
		User user = iservice.findByToken(token);
	    if (user != null) {
	    	System.out.println("pass");
	    	return ResponseEntity.ok("Authentication Successfull");
	    }
	    System.out.println("fail");
	    return ResponseEntity.ok("Authentication Failed");
	}
	
	@PostMapping("/set_password")
	public String setPassword(@PathVariable String email, @PathVariable String password) {
		iservice.updatePassword(email, password);
		return "Password Updated Successfully" ;
	}

}

package com.nagarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.model.Registration;
import com.nagarro.service.NotificationService;
import com.nagarro.service.RegService;

@RestController

public class RegistrationController {
	
	@Autowired
	private RegService service;
	
	@Autowired
	private NotificationService ns;
	@GetMapping("/")
	public String welcome()
	{
		return "Welcome";
	}
	 @PostMapping("/registration")
	    public String addUser(@RequestBody Registration registration) {
	        service.saveUser(registration);
	        try {
	        	ns.sendNotification(registration);
	        	
	        }
	        catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	        return "hi " + registration.getFirstName() + " you are successfully registered";
	    }

	

	    @GetMapping("/login/{email}")
	    public Registration findUserByName(@PathVariable String email) {
	         return service.getUsersByEmail(email);
	         
	    }
	    
	    @GetMapping("/forgot/{email}")
	    public String findUserByEmail(@PathVariable String email) {
	        service.getUsersByEmail(email);
	        try {
	        	String pass = ns.forgotPasswordMail(email);
	        	service.updateEmail(email,pass);
	        }
	        catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	        return "Your Password is successfully updated";
	    }

	    @PutMapping("/edit")
	    public Registration updateUser(@RequestBody Registration registration) {
	        return service.saveUser(registration);
	    }
	    
	    @GetMapping("/submit")
	    public Registration findUserById() {
	        return service.getUsersById();
	    }
	    
	    



}

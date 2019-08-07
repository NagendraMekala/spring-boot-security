package com.mng.java.controller;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mng.java.dao.UserDao;
import com.mng.java.model.Role;
import com.mng.java.model.User;

@RestController
@RequestMapping("/secure/rest")
public class AdminController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(path = "/admin/add", consumes = "application/json")
	public ResponseEntity<String> addUserByAdmin(@RequestBody User user) {
		System.out.println("****************ddUserByAdmin() executed****************");
		String password = user.getPassword();
		String enCryptPwd = passwordEncoder.encode(password);
		user.setPassword(enCryptPwd);
		userDao.save(user);
		return new ResponseEntity<String>("user added sucussfully",HttpStatus.OK);
	}
	
	@PostMapping("/admin/hello")
	public String demoPost() {
		return "demoPost method executed";
	}
	
	@GetMapping("/admin/get")
	public User getUser() {
		
		User user = new User();
		user.setUser_id(101);
		user.setUsername("nag");
		user.setPassword("nag");
		
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole_id(101);
		role.setRole("admin");
		roles.add(role);
		
		user.setRoles(roles);
		
		return user;
		
	}

}

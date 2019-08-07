package com.mng.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
public class AplicationController {

	
	@GetMapping("/process")
	public String process() {
		return "Application controller process method executed";
	}
}

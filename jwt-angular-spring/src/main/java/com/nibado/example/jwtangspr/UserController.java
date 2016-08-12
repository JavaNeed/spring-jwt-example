package com.nibado.example.jwtangspr;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Map<String, List<String>> userDb = new HashMap<>();

	public UserController() {
		userDb.put("tom", Arrays.asList("user"));
		userDb.put("sally", Arrays.asList("user", "admin"));
		userDb.put("john", Arrays.asList("user", "admin"));
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody final UserLogin login)throws ServletException {
		
		if (login.getName() == null || !userDb.containsKey(login.getName())) {
			throw new ServletException("Invalid login");
		}
		
		/* Lets set the JWT Claims */
		String token = Jwts.builder().setSubject(login.getName())
				.claim("roles", userDb.get(login.getName()))
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey")
				.compact();
		
		System.out.println("TOKEN : "+ token);
		
		return new LoginResponse(token);
	}
}

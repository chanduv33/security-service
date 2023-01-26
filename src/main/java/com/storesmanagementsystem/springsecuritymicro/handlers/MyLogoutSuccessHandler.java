package com.storesmanagementsystem.springsecuritymicro.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.springsecuritymicro.dto.ResponseClass;

@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		ResponseClass uresp = new  ResponseClass();
		uresp.setStatusCode(201);
		uresp.setMessage("Success");
		uresp.setDescription("You are successfully Logged out !");
		
		response.setStatus(200);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(uresp));
		
	}
}

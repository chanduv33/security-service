package com.storesmanagementsystem.springsecuritymicro.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.springsecuritymicro.dto.ResponseClass;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;
import com.storesmanagementsystem.springsecuritymicro.exceptions.DetailsNotFoundException;
import com.storesmanagementsystem.springsecuritymicro.exceptions.InvalidDetailsException;

@Component
public class MyAuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

//		UserInfoBean bean = UserDetailsServiceImpl.user;
		UserInfoBean bean = null;
		ResponseClass uresp = new ResponseClass();
		if (bean != null) {
			try {
				throw new InvalidDetailsException();
			} catch (InvalidDetailsException exp) {
				uresp.setStatusCode(407);
				uresp.setMessage("Failed");
				uresp.setDescription(exp.getMessage());
				response.setStatus(200);
			}
		} else {
			try {
				throw new DetailsNotFoundException();
			} catch (DetailsNotFoundException exp) {
				uresp.setStatusCode(408);
				uresp.setMessage("Failed");
				uresp.setDescription(exp.getMessage());
				response.setStatus(200);
			}
		}
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(uresp));

	}

}

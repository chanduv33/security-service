package com.storesmanagementsystem.springsecuritymicro.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;
import com.storesmanagementsystem.springsecuritymicro.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CustomUserPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment environment;
	private UserInfoBean bean;

	@Autowired
	private UserDetails userDetails;

//	@Override
//	protected String obtainUsername(HttpServletRequest request) {
//		if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
//			bean = null;
//			try {
//				UserInfoBean infoBean = getUserInfo(request);
//				return infoBean.getUsername();
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "";
//			}
//		}
//		return super.obtainUsername(request);
//	}

//	@Override
//	protected String obtainPassword(HttpServletRequest request) {
//
//		if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
//			try {
//				UserInfoBean infoBean = getUserInfo(request);
//				return infoBean.getPassword();
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "";
//			}
//		}
//
//		return super.obtainPassword(request);
//	}

//	private UserInfoBean getUserInfo(HttpServletRequest request) throws IOException {
//		if(bean==null) {
//			ObjectMapper mapper = new   ObjectMapper();
//			String json ="";
//			BufferedReader reader = request.getReader();
//			while(reader.ready()) {
//				json = json+reader.readLine();
//			}
//			bean = mapper.readValue(json, UserInfoBean.class);
//		}
//		return bean;
//	}

	@Autowired
	public CustomUserPasswordAuthFilter(UserService userService, Environment environment) {
		this.userService = userService;
		this.environment = environment;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();
		String expire = environment.getProperty("token.expire");
		UserInfoBean user = userService.getUserByEmail(username);
		String token = Jwts.builder().setSubject(String.valueOf(user.getUserId()))
				.setExpiration(
						new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expire"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
		response.setHeader("token", token);
		response.setHeader("userId", String.valueOf(user.getUserId()));
		response.setHeader("role", user.getRole());
//		ResponseClass uresp = new ResponseClass();
//		uresp.setStatusCode(201);
//		uresp.setMessage("Success");
//		uresp.setDescription("Login Successfull");
//		response.setStatus(200);
//		ObjectMapper mapper = new ObjectMapper();
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		response.getWriter().write(mapper.writeValueAsString(uresp));
//		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserInfoBean userInfo = new ObjectMapper().readValue(request.getInputStream(), UserInfoBean.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					userInfo.getUsername(), userInfo.getPassword(), new ArrayList<>()));
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

//		// TODO Auto-generated method stub
//		return super.attemptAuthentication(request, response);
	}

}

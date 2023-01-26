package com.storesmanagementsystem.springsecuritymicro.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AppFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
//		res.setHeader("Access-Control-Allow-Origin", "*");
//		res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//		res.setHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,PUT,OPTIONS");
//		res.setHeader("Access-Control-Allow-Headers", "*");
//		res.setHeader("Access-Control-Max-Age", "86400");
		 if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
				res.setStatus(Response.SC_OK);
	        }
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

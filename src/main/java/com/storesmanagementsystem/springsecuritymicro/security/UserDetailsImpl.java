package com.storesmanagementsystem.springsecuritymicro.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.storesmanagementsystem.springsecuritymicro.dto.UserInfoBean;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Component
public class UserDetailsImpl implements UserDetails {

	private UserInfoBean bean;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(bean.getRole());
		
		return Arrays.asList(auth);
	}

	@Override
	public String getPassword() {
		return bean.getPassword();
	}

	@Override
	public String getUsername() {
		return bean.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

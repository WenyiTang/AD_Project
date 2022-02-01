package com.example.adproject.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.adproject.helper.RoleEnum;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
			Authentication authentication) throws IOException, ServletException {

		boolean isUser = false;
		boolean isAdmin = false;
	
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		
		if (isUser) {
			redirectStrategy.sendRedirect(arg0, arg1, "/dashboard");
		} else if (isAdmin) {
			redirectStrategy.sendRedirect(arg0, arg1, "/adminDashboard");
		} else {
			throw new IllegalStateException(); 
		}
	}
}

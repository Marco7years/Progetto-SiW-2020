package com.example.demo.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.Credentials;
import com.example.demo.model.User;
import com.example.demo.repository.CredentialsRepository;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
	
	private User user;
	private Credentials credentials;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	public Credentials getLoggedCredentials() {
		if(this.credentials == null)
			this.update();
		return this.credentials;
	}
	
	public User getLoggedUser() {
		if(this.user == null)
			this.update();
		return this.user;
	}
	
	public void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUserDetails = (UserDetails) obj;
		
		this.credentials = this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
		this.credentials.setPassword("[PROTECTED]");
		this.user = this.credentials.getUser();
	}
}

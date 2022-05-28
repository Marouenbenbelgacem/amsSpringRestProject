
package com.sip.ams.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if ("amine".equals(username)) {
			return new User("amine", "$2a$12$fVNASeWu2cDMYe/fXAWkreS7rWVgMGxHUGkJQsKtMjURGU.LkRwb2",new ArrayList<>());
			} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
			}

	}

}

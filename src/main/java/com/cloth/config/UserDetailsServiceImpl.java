package com.cloth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloth.Dao.UserDto;
import com.cloth.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDto userDto;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		User user = userDto.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could not Found user !!");
			
		}
		CustomerUserDetail customerUserDetail = new CustomerUserDetail(user); 
		
		return customerUserDetail;
	}

}

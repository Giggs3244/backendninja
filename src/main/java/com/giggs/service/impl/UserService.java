package com.giggs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.giggs.entity.UserRole;
import com.giggs.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.giggs.entity.User user = userRepository.findByUsername(username);
		
//		List<GrantedAuthority> authorities = buildAuthorities((user != null) ? user.getRoles() : null);
		if(user == null) {
			throw new UsernameNotFoundException("Username: " + username + " not found.");
		}
		List<GrantedAuthority> authorities = buildAuthorities(user.getRoles());
		return buildUser(user, authorities);
	}

	private User buildUser(com.giggs.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUser(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles) {
		/*
		 * Set<GrantedAuthority> auths = new HashSet<>(); for(User userRole :
		 * userRoles) { auths.add(new
		 * SimpleGrantedAuthority(userRole.getRole())); } return new
		 * ArrayList<>(auths);
		 */
//		if (userRoles != null) {
			return new ArrayList<>(userRoles.stream().map(ur -> new SimpleGrantedAuthority(ur.getRole()))
					.collect(Collectors.toList()));
//		}
//		return null;
	}

}

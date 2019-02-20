package com.epam.spring.advanced.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.spring.core.dao.UserDAO;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Qualifier("UserDAODB")
	UserDAO userDAO;

	private static final String ROLE_PREFIX = "ROLE_";

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		List<com.epam.spring.core.model.User> l =  userDAO.getAll();

		com.epam.spring.core.model.User userEntity = userDAO.findByName(userName);
		if (userEntity == null) {
			throw new UsernameNotFoundException("user name not found");
		}
		return buildUserFromUserEntity(userEntity);
	}

	private UserDetails buildUserFromUserEntity(com.epam.spring.core.model.User userEntity) {
		String username = userEntity.getName();
		String password = userEntity.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (String role:  Arrays.asList(userEntity.getRoles().split("\\s*,\\s*"))){
		authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
		}
		User springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		return springUser;
	}

}

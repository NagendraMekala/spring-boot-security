package com.mng.java.user.details.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mng.java.model.User;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = new User();
		@SuppressWarnings("unchecked")
		List<User> list = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username")
		              .setParameter("username", username)
		             .getResultList();
		
		if (!list.isEmpty()) {
			user = (User) list.get(0);
			System.out.println("***User Object***: "+ user.getUsername());
		}else {
			throw new UsernameNotFoundException("user not exist with name: "+username);
		}
		
		UserDetailsImpl detailsImpl = new UserDetailsImpl();
		detailsImpl.setUser(user);
		return detailsImpl;
	}

}

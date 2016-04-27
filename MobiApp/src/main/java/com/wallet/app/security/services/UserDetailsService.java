package com.wallet.app.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.repo.UserAccountRepo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	private UserAccountRepo<UserAccount> userAccountRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountRepo.findUser(username);
		System.out.println("User Account Authorization ::"+userAccount.getAuthorities());
		return new User(userAccount.getEmail(),userAccount.getPassword(),userAccount.getAuthorities());
	}

}

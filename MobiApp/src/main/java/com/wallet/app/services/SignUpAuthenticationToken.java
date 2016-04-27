package com.wallet.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserPersistentToken;
import com.wallet.app.persist.repo.UserAccountRepo;

@Service
public class SignUpAuthenticationToken {
	
	@Autowired
	private UserAccountRepo<UserAccount> userAccountRepo;

	public UserPersistentToken generateSignUpToken(String username){
		return null;
	}
	public UserPersistentToken generateSignUpToken(UserAccount user){
		
		
		return null;
	}
}

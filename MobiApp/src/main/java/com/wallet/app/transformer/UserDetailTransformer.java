package com.wallet.app.transformer;

import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;

import com.wallet.app.persist.entity.UserAccount;

public class UserDetailTransformer implements Transformer<UserDetails, UserAccount>{

	@Override
	public UserDetails transform(UserAccount user) throws ServiceException {
		org.springframework.security.core.userdetails.User userDetail = 
				new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), user.getAuthorities());
		return userDetail;
	}

}

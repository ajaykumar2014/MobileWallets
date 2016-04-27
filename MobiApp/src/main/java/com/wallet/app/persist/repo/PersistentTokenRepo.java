package com.wallet.app.persist.repo;

import com.wallet.app.persist.entity.UserAccount;

public interface PersistentTokenRepo<T> {
	
	public T createToken(T user);
	
	public boolean verifyAuthToken(UserAccount user,String authCode);
	

}

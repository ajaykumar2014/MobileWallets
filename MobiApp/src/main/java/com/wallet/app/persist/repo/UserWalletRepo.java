package com.wallet.app.persist.repo;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserWallet;

public interface UserWalletRepo{
	
	public UserWallet transfer(UserAccount fromAccount,UserAccount targetUser,long amt);
	
	/*public void depositeAmtToTargetWallet(U targetUser,long amt);
	
	public W updateCurrentUserWallet(U targetUser,long amt);*/
	
	

}

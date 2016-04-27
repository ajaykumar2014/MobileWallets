package com.wallet.app.persist.repo;

import java.util.List;

import com.wallet.app.persist.entity.UserAccount;


public interface UserAccountRepo<U> {

	U findUserByEmail(String email);
	
	U findUser(String username);

	U findUserByMobile(String mobile);

	U findUserByAccount(String account);

	U updateUserAccount(U user);

	boolean verifyUserAccount(U user, String verificaitonCode);

	List<U> getUsers();
	
	Long Save(U user);
	
	void Update(U user);
	
	boolean verifyUserExistAlready(U user);
	
	UserAccount getUserWalletByAccountNumber(String accountNumber);
	
	UserAccount getUserWalletTransactionByAccountNumber(String accountNumber);
	
	UserAccount getUserWallet(String username);
	
	List<UserAccount> getUserWalletTransactionHistory(String username);

	UserAccount getUserWallet(UserAccount user);
	
	boolean isUserAccountExist(String accountNumber);

}

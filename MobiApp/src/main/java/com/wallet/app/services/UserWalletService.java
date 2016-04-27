package com.wallet.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.wallet.app.exception.UserAccountNumberNotFoundExcpetion;
import com.wallet.app.model.Response;
import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserWallet;
import com.wallet.app.persist.repo.UserAccountRepo;
import com.wallet.app.persist.repo.UserWalletRepo;

@Service("userWalletService")
public class UserWalletService {
	@Autowired
	private UserAccountRepo<UserAccount> userAccountRepo;

	@Autowired
	private UserWalletRepo userWalletRepo;


	public UserWalletService(){
		
	}

	public Response transfer(UserAccount currentAccount,String targetUserAccountNumber, long amt) {
		try {
			UserAccount targetUserAccount = verifyAndGetTargetUserAccount(targetUserAccountNumber);
			UserWallet currentUserWallet = getUserWallet(currentAccount).getUserWallet();
			if (amt < currentUserWallet.getBalance()) {
				userWalletRepo.transfer(currentAccount,targetUserAccount, amt);
			}
			return new Response(HttpStatus.OK.value(), String.format(
					"Amount %d has been successfully transfer to Account Number %s", amt, targetUserAccountNumber));
		} catch (Exception e) {
			return new Response(HttpStatus.NOT_FOUND.value(), e.getMessage());
		}

	}

	public UserAccount getUserWallet(UserAccount user) {
		return userAccountRepo.getUserWallet(user);
	}

	public UserAccount verifyAndGetTargetUserAccount(String accountNumber) {
		UserAccount user = userAccountRepo.getUserWalletByAccountNumber(accountNumber);
		if (user == null) {
			throw new UserAccountNumberNotFoundExcpetion("Target User Account Not Found !!");
		}
		return user;
	}

}

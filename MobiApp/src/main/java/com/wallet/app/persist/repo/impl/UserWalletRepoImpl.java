package com.wallet.app.persist.repo.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserWallet;
import com.wallet.app.persist.entity.WalletTransaction;
import com.wallet.app.persist.repo.UserWalletRepo;
import com.wallet.app.services.SecureAlphaNumericGeneratorUtils;
@Repository
public class UserWalletRepoImpl implements UserWalletRepo {


	@Autowired
	private SessionFactory sessionFactory;

	private final Date CURRENT_TRANSACTION_TIMESTAMP = Calendar.getInstance().getTime();

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public UserWalletRepoImpl(){}


	@Transactional
	@Override
	public UserWallet transfer(UserAccount currentAccount, UserAccount targetUser, long amt) {

		depositeAmtToTargetWallet(currentAccount,targetUser, amt);
		UserWallet currentUserWallet = updateCurrentUserWallet(currentAccount,targetUser, amt);
		return currentUserWallet;
	}
	
	

	private void depositeAmtToTargetWallet(UserAccount currentAccount,UserAccount targetUser, long amt) {

		UserWallet targetUserWallet = targetUser.getUserWallet();
		targetUserWallet.setBalance(targetUserWallet.getBalance() + amt);

		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setAmt(amt);
		walletTransaction.setDebitAmt(amt);
		walletTransaction.setCreditAmt(0);
		walletTransaction.setCounterPartyAccountNumber(currentAccount.getUserWallet().getAccountNumber());
		walletTransaction.setTransactionDate(CURRENT_TRANSACTION_TIMESTAMP);
		walletTransaction.setUserWallet(targetUser.getUserWallet());

		targetUserWallet.getUserWalletTransaction().add(walletTransaction);
		getSession().save(targetUserWallet);

	}

	private UserWallet updateCurrentUserWallet(UserAccount currentAccount,UserAccount targetUser, long amt) {
		UserWallet currentUserWallet = currentAccount.getUserWallet();

		currentUserWallet.setBalance(currentUserWallet.getBalance() - amt);

		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setAmt(amt);
		walletTransaction.setDebitAmt(0);
		walletTransaction.setCreditAmt(amt);
		walletTransaction.setTransactionNumber(SecureAlphaNumericGeneratorUtils.generateNumericCode(12));
		walletTransaction.setCounterPartyAccountNumber(targetUser.getUserWallet().getAccountNumber());
		walletTransaction.setTransactionDate(CURRENT_TRANSACTION_TIMESTAMP);
		walletTransaction.setUserWallet(targetUser.getUserWallet());

		currentUserWallet.getUserWalletTransaction().add(walletTransaction);
		getSession().save(currentUserWallet);

		return currentUserWallet;
	}

}

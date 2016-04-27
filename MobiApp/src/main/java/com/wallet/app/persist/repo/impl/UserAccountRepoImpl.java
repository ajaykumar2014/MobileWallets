package com.wallet.app.persist.repo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserWallet;
import com.wallet.app.persist.repo.UserAccountRepo;

@Repository
@Transactional
public class UserAccountRepoImpl implements UserAccountRepo<UserAccount> {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public UserAccount findUserByEmail(String email) {
		Query q = getSession().getNamedQuery("getUserByEmail").setString("email", email);
		return (UserAccount)q.uniqueResult();
	}

	@Override
	public UserAccount findUser(String username) {
		List list = getSession().createCriteria(UserAccount.class)
				.add(Restrictions.or(Restrictions.eq("email", username).ignoreCase(),
						Restrictions.eq("phone", username)))
				.list();
		if( !list.isEmpty() && list.size() >0){
			UserAccount account = (UserAccount)list.get(0);
			account.setUsername(username);
			return account;
		}
		return null;
	}
	
	public UserAccount getUserWallet(String username) {
		UserAccount userAccount = (UserAccount) getSession()
				.createCriteria(UserAccount.class, "userAccount")
				.createAlias("userAccount.userWallet", "wallet")
				.setFetchMode("userAccount.userWallet", FetchMode.JOIN).add(Restrictions
						.or(Restrictions.eq("email", username).ignoreCase(), Restrictions.eq("phone", username)))
				.uniqueResult();
		return userAccount;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAccount> getUserWalletTransactionHistory(String username){
		
		List<UserAccount> userWalletTransactionHistory = (List<UserAccount>)  getSession()
				.createCriteria(UserAccount.class, "userAccount")
				.createAlias("userAccount.userWallet", "wallet")
				.createAlias("userAccount.userWallet.userWalletTransaction", "walletTransaction")
				.setFetchMode("userAccount.userWallet", FetchMode.JOIN)
				.setFetchMode("userAccount.userWallet.userWalletTransaction", FetchMode.JOIN).
				add(Restrictions
						.or(Restrictions.eq("email", username).ignoreCase(), Restrictions.eq("phone", username)))
				.list();
		return userWalletTransactionHistory;
	}
	

	@Override
	public UserAccount findUserByMobile(String mobile) {
		Query q = getSession().getNamedQuery("getUserByPhone").setString("phone", mobile);
		return (UserAccount)q.uniqueResult();
	}

	@Override
	public UserAccount findUserByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount updateUserAccount(UserAccount user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyUserAccount(UserAccount user, String verificaitonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserAccount> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long  Save(UserAccount user) {
		return (Long)getSession().save(user);
	}

	@Override
	public void Update(UserAccount user) {
		getSession().update(user);
	}
	@Override
	public boolean verifyUserExistAlready(UserAccount user) {
		List list = getSession().createCriteria(UserAccount.class)
				.add(Restrictions.or(Restrictions.eq("email", user.getEmail()).ignoreCase(),
						Restrictions.eq("phone", user.getPassword())))
				.list();
		if (list.size() == 0)
			return true;

		return false;

	}

	@Override
	public UserAccount getUserWalletByAccountNumber(String accountNumber) {
		Query q = getSession().getNamedQuery("getUserByAccountNumber")
				.setString("accountNumber", accountNumber);
		return (UserAccount)q.uniqueResult();
	}

	@Override
	public UserAccount getUserWallet(UserAccount user) {
		UserWallet wallet = null;
		if( user.getUserWallet() == null ){
			wallet = (UserWallet) getSession().get(UserWallet.class,user.getId());
			user.setUserWallet(wallet);
		}
		return user;
	}

	@Override
	public boolean isUserAccountExist(String accountNumber) {
		getSession().getNamedQuery("getUserByAccountNumber").setString("accountNumber", accountNumber);
		return false;
	}

	@Override
	public UserAccount getUserWalletTransactionByAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

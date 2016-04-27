package com.wallet.app.persist.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USER_Wallet")
@XmlRootElement(name = "eWallet")
@XmlAccessorType(XmlAccessType.NONE)
public class UserWallet {

	@Id
	@GeneratedValue
	private long id;

	@Column(unique=true,nullable=false)
	private String accountNumber;

	@Column(name = "Wallet_Balance")
	private long balance = 100;

	@Column(name = "Created_Date")
	private long created_date;

	@Column(name = "Transaction_TIMESTAMP")
	private Date last_transaction = Calendar.getInstance().getTime();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userWallet", fetch = FetchType.LAZY)
	private Set<WalletTransaction> userWalletTransaction = new HashSet<WalletTransaction>();

	public UserWallet() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getCreated_date() {
		return created_date;
	}

	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}

	public Date getLast_transaction() {
		return last_transaction;
	}

	public void setLast_transaction(Date last_transaction) {
		this.last_transaction = last_transaction;
	}

	public Set<WalletTransaction> getUserWalletTransaction() {
		return userWalletTransaction;
	}

	public void setUserWalletTransaction(Set<WalletTransaction> userWalletTransaction) {
		this.userWalletTransaction = userWalletTransaction;
	}

}

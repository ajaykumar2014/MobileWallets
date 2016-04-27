package com.wallet.app.persist.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USER_Wallet_Transaction")
@XmlRootElement(name = "eWalletTransaction")
@XmlAccessorType(XmlAccessType.NONE)
public class WalletTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2189566219817925975L;
	@Id
	@GeneratedValue
	private long id;

	@Column(name = "counterPartyAccountNumber")
	private String counterPartyAccountNumber;

	private long creditAmt = 0;
	private long debitAmt =0 ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Transaction_TIMESTAMP")
	private Date transactionDate = Calendar.getInstance().getTime();
	
	@Column(name="Amount")
	private long amt =0;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Wallet_ID")
	private UserWallet userWallet;
	
	@Column(name="Transaction_Number",nullable=false)
	private String transactionNumber;

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getCounterPartyAccountNumber() {
		return counterPartyAccountNumber;
	}

	public void setCounterPartyAccountNumber(String counterPartyAccountNumber) {
		this.counterPartyAccountNumber = counterPartyAccountNumber;
	}

	public long getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(long creditAmt) {
		this.creditAmt = creditAmt;
	}

	public long getDebitAmt() {
		return debitAmt;
	}

	public void setDebitAmt(long debitAmt) {
		this.debitAmt = debitAmt;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public long getAmt() {
		return amt;
	}

	public void setAmt(long amt) {
		this.amt = amt;
	}

	public UserWallet getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}



}

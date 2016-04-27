package com.wallet.app.persist.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Auth_Token")
public class UserPersistentToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3588341078323087972L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String userToken;

	@Column(nullable = false, unique = true)
	private String email;

	private String authCode;

	private long expiryDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = Calendar.getInstance().getTime();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}

package com.wallet.app.persist.entity;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wallet.app.constants.UserRole;

@Entity
@Table(name = "USER_DETAILS", uniqueConstraints = @UniqueConstraint(columnNames = { "email", "phone" }) )
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({ 
		@NamedQuery(name = "getUserByEmail", query = "from UserAccount where email = :email"),
		@NamedQuery(name = "getUserByPhone", query = "from UserAccount where Phone = :phone"),
		@NamedQuery(name = "getUserByAccountNumber", query = "from UserAccount u inner join u.userWallet uw where u.isVerify = true and uw.accountNumber = :accountNumber")
		//@NamedQuery(name = "getUserWalletByAccountNumber", query = "from UserAccount u inner join u.userWallet w inner join w.userWalletTransaction wt where u.isVerify = true and wt.accountNumber = :accountNumber")
		})
public class UserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2779244168551721663L;

	@Id
	@GeneratedValue
	private long id;

	private String firstName;

	private String lastName;

	@XmlElement
	@Column(nullable = false)
	private String email;

	@XmlElement
	@Column(unique = true)
	private String phone;
	
	@Transient
	@XmlElement
	@Column(unique = true)
	private String username;

	@XmlElement
	@Column(nullable = false)
	private String password;
	
	@JsonIgnore
	@Column(name = "isVerify", nullable = false)
	private boolean isVerify = false;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserAuthority> authorities;

	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "Wallet_ID")
	private UserWallet userWallet;
	
	private String authCode;

	@JsonIgnore
	private long authCodeExpiryDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserWallet getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}

	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	@JsonIgnore
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}

	// Use Roles as external API
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(UserRole.valueOf(authority));
			}
		}
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public long getAuthCodeExpiryDate() {
		return authCodeExpiryDate;
	}

	public void setAuthCodeExpiryDate(long authCodeExpiryDate) {
		this.authCodeExpiryDate = authCodeExpiryDate;
	}
	
	
	
}

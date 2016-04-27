package com.wallet.app.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallet.app.constants.UserRole;
import com.wallet.app.model.Response;
import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserWallet;
import com.wallet.app.persist.repo.UserAccountRepo;

@Service
public class UserLoginService {
	@Autowired
	private UserAccountRepo<UserAccount> userAccountRepo;

	private final Date CURRENT_TIME_STAMP = Calendar.getInstance().getTime();

	private UserWallet createNewWallet() {
		UserWallet wallet = new UserWallet();
		wallet.setCreated_date(Calendar.getInstance().getTimeInMillis());
		wallet.setBalance(100);
		wallet.setLast_transaction(Calendar.getInstance().getTime());
		wallet.setAccountNumber(SecureAlphaNumericGeneratorUtils.generateNumericCode());
		return wallet;
	}

	public Response signup(UserAccount user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!user.getRoles().isEmpty()) {
			for (UserRole role : user.getRoles()) {
				user.grantRole(role);
			}
		} else {
			user.grantRole(UserRole.USER);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setVerify(false);
		user.setAuthCodeExpiryDate(CURRENT_TIME_STAMP.getTime());
		user.setAuthCode(SecureAlphaNumericGeneratorUtils.generateAlphaNumericCode(5));
		user.setUserWallet(createNewWallet());
		Long identifier = userAccountRepo.Save(user);
		if (identifier != null && identifier > 0) {
			return new Response(HttpStatus.OK.value(), "User Created Successfully But Not yet verified");
		}
		return new Response(HttpStatus.BAD_REQUEST.value(), "",
				new com.wallet.app.model.Error("Invalid Request", "Invalid Request Parameters..."));
	}



	public Response signupAndVerify(String username, String authCode) {
		UserAccount userAccount = userAccountRepo.findUser(username);

		if (userAccount != null && !userAccount.isVerify() && (userAccount.getAuthCode().equalsIgnoreCase(authCode))) {
			userAccount.setVerify(true);
			userAccountRepo.Update(userAccount);
			return new Response(HttpStatus.OK.value(), "User Authentication Verify Successfully !!");
		} else if (userAccount != null && userAccount.isVerify()) {
			return new Response(HttpStatus.FOUND.value(), "User has already authenticated !!");
		} else {
			return new Response(HttpStatus.NOT_FOUND.value(), "User Not Found !!!");
		}
	}

}

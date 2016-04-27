package com.wallet.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.app.model.Response;
import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.services.UserLoginService;

@RestController
public class UserAcccountController {

	@Autowired
	private UserLoginService userLoginService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response signup(@RequestBody UserAccount user) {
		return userLoginService.signup(user);
	}

	@RequestMapping(value = "/verify/{authCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response signup(@PathVariable(value="authCode") String authCode, @RequestParam(value="username",required=true) String username) {
		return userLoginService.signupAndVerify(username, authCode);
	}

	/*
	 * @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes
	 * = MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public @ResponseBody Response
	 * verifyNewUser(@RequestBody UserAccount user,@RequestParam String
	 * authCode) {
	 * 
	 * return userLoginService.signup(user);
	 * 
	 * }
	 */

}

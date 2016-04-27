package com.wallet.app.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWalletController {
	
	@RequestMapping(value = "/transfer/{amount}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String transfer(@PathVariable(value="amount") String amount, @RequestParam(value="username",required=true) String username) {
		return "Amount "+amount+" is tranferred to account "+username;
	}

}

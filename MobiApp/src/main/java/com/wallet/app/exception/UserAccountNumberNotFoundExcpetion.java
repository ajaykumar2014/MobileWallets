package com.wallet.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="Account Number Not Found !!")
public class UserAccountNumberNotFoundExcpetion extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 312211581948634291L;

	public UserAccountNumberNotFoundExcpetion(String msg){
		super(msg);
	}
	
	public UserAccountNumberNotFoundExcpetion(String msg,Throwable throwable){
		super(msg,throwable);
	}
}

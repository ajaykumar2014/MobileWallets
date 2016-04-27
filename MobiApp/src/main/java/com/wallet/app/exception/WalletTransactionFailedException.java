package com.wallet.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="Wallet Transfer Failed")
public class WalletTransactionFailedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 312211581948634291L;

	public WalletTransactionFailedException(String msg){
		super(msg);
	}
	
	public WalletTransactionFailedException(String msg,Throwable throwable){
		super(msg,throwable);
	}
}
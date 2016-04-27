package com.wallet.app.security.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.app.constants.UserRole;

public final class TokenHandler {

	private static final String HMAC_ALGO = "HmacSHA256";
	private static final String SEPARATOR = ".";
	private static final String SEPARATOR_SPLITTER = "\\.";

	private final Mac hmac;

	public TokenHandler(byte[] secretKey)  {
		try {
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}

	public User parseUserFromToken(String token) {
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			try {
				final byte[] userBytes = fromBase64(parts[0]);
				final byte[] hash = fromBase64(parts[1]);

				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				if (validHash) {
					final User user = fromJSON(userBytes);
					//if (new Date().getTime() < user.getExpires()) {
						return user;
					//}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String createTokenForUser(org.springframework.security.core.userdetails.UserDetails user) {
		byte[] userBytes=toJSON(user);
		byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}

	private User fromJSON(final byte[] userBytes) {
		try {
			return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private byte[] toJSON(org.springframework.security.core.userdetails.UserDetails user) {
		try {
			return new ObjectMapper().writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	private byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}

	// synchronized to guard internal hmac object
	private synchronized byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}

	public static void main(String[] args) {
		Date start = new Date();
		byte[] secret = new byte[70];
		new java.security.SecureRandom().nextBytes(secret);
		TokenHandler tokenHandler = new TokenHandler(secret);
		//for (int i = 0; i < 1000; i++) {
			/*final User user = new User(java.util.UUID.randomUUID().toString().substring(0, 8), new Date(
					new Date().getTime() + 10000));
			user.grantRole(UserRole.ADMIN);*/
			//final String token = tokenHandler.createTokenForUser(user);
		System.out.println(tokenHandler.parseUserFromToken("eyJwYXNzd29yZCI6bnVsbCwidXNlcm5hbWUiOiJhamF5MS5qYW1pYWhhbWRhcmRAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlt7ImlkIjoxNCwiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJhY2NvdW50Tm9uRXhwaXJlZCI6dHJ1ZSwiYWNjb3VudE5vbkxvY2tlZCI6dHJ1ZSwiY3JlZGVudGlhbHNOb25FeHBpcmVkIjp0cnVlLCJlbmFibGVkIjp0cnVlfQ==.GHoNzEUZU9JONHKzyoOrJaHci+wFXZcqUBGxN5Zqd60="));
			//	System.out.println("error");
			//}
		//}
		//System.out.println(parsedUser.getUsername());
	}

}
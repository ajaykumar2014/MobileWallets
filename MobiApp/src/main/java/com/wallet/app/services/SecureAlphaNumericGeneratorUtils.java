package com.wallet.app.services;

import java.security.SecureRandom;
import java.util.Random;

public class SecureAlphaNumericGeneratorUtils {
	private static char validAlphaNumericCode[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879"
			.toCharArray();
	private static char validNumericCode[] = "0123456879".toCharArray();

	private static int MAX_CODE_GENERATOR = 10;

	private static String generateCode(final int MAX_CODE_GENERATOR, final char[] validAlphaNumericCode) {
		SecureRandom secure = new SecureRandom();
		Random random = new SecureRandom();
		char[] buffer = new char[MAX_CODE_GENERATOR];
		for (int i = 0; i < MAX_CODE_GENERATOR; i++) {
			if ((i % 10) == 0) {
				random.setSeed(secure.nextLong());
			}
			buffer[i] = validAlphaNumericCode[random.nextInt(validAlphaNumericCode.length)];
		}

		return new String(buffer).toUpperCase();
	}

	public static String generateNumericCode(final int max_code_generators) {

		return generateCode(max_code_generators, validNumericCode);
	}

	public static String generateAlphaNumericCode(final int max_code_generators) {

		return generateCode(max_code_generators, validAlphaNumericCode);
	}

	public static String generateAlphaNumericCode() {

		return generateCode(MAX_CODE_GENERATOR, validAlphaNumericCode);
	}

	public static String generateNumericCode() {
		return generateCode(MAX_CODE_GENERATOR, validNumericCode);
	}
}

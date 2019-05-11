package io.mcauth.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCrypt {
	public static String md5(String str) {
		return hash(str, "MD5");
	}

	public static String sha1(String str) {
		return hash(str, "SHA-1");
	}

	public static String sha256(String str) {
		return hash(str, "SHA-256");
	}

	public static String sha512(String str) {
		return hash(str, "SHA-512");
	}

	public static String hash(String str, String type) {
		try {
			MessageDigest md = MessageDigest.getInstance(type);
			md.update(str.getBytes());
			byte[] digest = md.digest();
			return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}

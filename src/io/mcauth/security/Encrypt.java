package io.mcauth.security;

public class Encrypt {
	public static String encrypt(String str, String type) {
		switch (type) {
		case "MD5":
			return HashCrypt.md5(str);
		case "SHA-1":
			return HashCrypt.sha1(str);
		case "SHA-256":
			return HashCrypt.sha256(str);
		case "SHA-512":
			return HashCrypt.sha512(str);
		case "BCrypt":
			return BCrypt.hashpw(str, BCrypt.gensalt());
		default:
			return "";
		}
	}

	public static boolean check(String plaintext, String hash, String type) {
		switch (type) {
		case "MD5":
			return (HashCrypt.md5(plaintext).equals(hash));
		case "SHA-1":
			return (HashCrypt.sha1(plaintext).equals(hash));
		case "SHA-256":
			return (HashCrypt.sha256(plaintext).equals(hash));
		case "SHA-512":
			return (HashCrypt.sha512(plaintext).equals(hash));
		case "BCrypt":
			return (BCrypt.checkpw(plaintext, hash));
		default:
			return false;
		}
	}
}

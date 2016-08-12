package net.commom.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.security.crypto.codec.Hex;

import net.common.rest.TokenUtils;

public class JWTTokenTest {

	private static String createToken(){
		// expires in 1 hour
		long expires = System.currentTimeMillis()+ 1000L * 60 * 60;
		
		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append("john");
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(computeSignature(expires));

		System.out.println("TOKEN :"+tokenBuilder.toString());
		
		return tokenBuilder.toString();
	}
	
	
	public static String computeSignature(long expires){
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append("john");
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append("test123");
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		System.out.println("COMPUTE SIGNATURE : "+new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes()))));
		
		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}
	
	public static String getUserNameFromToken(String authToken){
		if (null == authToken) {
			return null;
		}

		// validate token
		if(validateToken(authToken))
			System.out.println("Token Validated Successfully !!!");
		else
			throw new IllegalStateException("Token did not get mached, causing problems");
		
		String[] parts = authToken.split(":");
		System.out.println("GET USERNAME FROM TOKEN : "+parts[0]);
		return parts[0];
	}
	
	
	public static boolean validateToken(String authToken){
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(computeSignature(expires));
	}
	
	
	@Test
	public void test() {
		JWTTokenTest jwt = new JWTTokenTest();
		String token = jwt.createToken();
		String username = getUserNameFromToken(token);
		System.out.println("UserName : "+username);
	}
}

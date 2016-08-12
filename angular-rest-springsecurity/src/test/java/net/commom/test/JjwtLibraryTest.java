package net.commom.test;

import java.security.SecureRandom;
import java.util.Date;

import org.junit.Test;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// @Ref URL: http://stackoverflow.com/questions/29744385/how-to-extend-expiration-time-java-json-web-token

public class JjwtLibraryTest {

	@Test
	public void test() {
		byte[] key = new byte[64];
		new SecureRandom().nextBytes(key);
		Date date = new Date();
		long t = date.getTime();
		Date expirationTime = new Date(t + 5000l); // set 5 seconds

		String compact1 = Jwts.builder().setSubject("Joe").setExpiration(expirationTime).signWith(SignatureAlgorithm.HS256, key).compact();
		System.out.println("compact-1 : " + compact1);
		try {
			//Create a *new* token that reflects a longer extended expiration time.
			Date date1 = new Date();
			long t1 = date1.getTime();
			Date expirationTime1 = new Date(t1 + 5000l); //prolongation 5 seconds

			String compact2 = Jwts.builder().setSubject("Joe").setExpiration(expirationTime1).signWith(SignatureAlgorithm.HS256, key).compact();
			System.out.println("Compact-2 :"+ compact2);
			
			// check if the extend expiration work.
			Thread.sleep(3000);
			System.out.println("unpackage 2 : " + Jwts.parser().setSigningKey(key).parseClaimsJws(compact2).getBody().getSubject());

			Thread.sleep(1000);
		} catch (InterruptedException | ExpiredJwtException ex) {
			System.out.println("exception : " + ex.getMessage());
			Thread.currentThread().interrupt();
		}
	}
}

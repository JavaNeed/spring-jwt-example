import java.util.Date;

import org.joda.time.DateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTokenDemo {
	
	private static String gererateToken(){
		String token = Jwts.builder().setSubject("tom")
				.claim("roles", "tom")
				.setExpiration(addOneHour(new Date()))
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey") // Algo, secretKey
				.compact();
		System.out.println("TOKEN : "+ token);
		return token;
	}
	
	public static void main(String[] args) {
		final Claims claims = Jwts.parser()
				.setSigningKey("secretkey")
				.parseClaimsJws(gererateToken()).getBody();
		
		System.out.println(claims);
		System.out.println("----------------------------");
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
		System.out.println("Roles :"+claims.getAudience());
	}
	
	private static Date addOneHour(Date systemDate){
		DateTime dt = new DateTime(systemDate);
		Date added = dt.plusHours(1).toDate();
		return added;
	}
}

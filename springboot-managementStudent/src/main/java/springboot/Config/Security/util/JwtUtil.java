package springboot.Config.Security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

//	@Autowired
//	private UserService userSer;
	
	private String secretKey = "secret";
	
	private Claims getAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private String createToken(String username, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 3600*10000))
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
				.compact();
	}
	
//	public String createForgotPasswordToken(String username) {
//		return Jwts.builder().setSubject(username)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis()+ 60*10*1000))
//				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
//				.compact();
//	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(username, claims);
	}
	
	public boolean validateToken(String token, UserDetails userDetails ) {
		String username = extractUsername(token);
		return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
	}

}


package com.in28munites.microservices.apigateway;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.validity}")
	private long tokenValidity;

	public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public String generateToken(String id) {
		Claims claims = Jwts.claims().setSubject(id);
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
	}

	public void validateToken(final String token) throws Exception {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new Exception("Invalid JWT signature");
		}
	}

	public static void getNumber(int a) {
		a = 1;
	}

	public static void main(String args[]) {
		int a = 10;
		JwtUtil.getNumber(a);
		System.out.println(a);
	}
}

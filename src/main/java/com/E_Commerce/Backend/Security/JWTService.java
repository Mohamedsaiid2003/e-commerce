package com.E_Commerce.Backend.Security;


import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    private String secretkey = "cF781A";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {

        }
    }


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .and()
                .signWith(getKey())
                .compact();
    }


    private SecretKey getKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyByte);
    }


    public String extractUserName(String token) {
        return extractClaim(token , Claims::getSubject);
    }
    private <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build().
                parseSignedClaims(token).
                getPayload();
    }
    public boolean valideteToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpirthion(token).before(new Date());
    }
    private Date extractExpirthion(String token){
        return extractClaim(token , Claims::getExpiration);
    }


}

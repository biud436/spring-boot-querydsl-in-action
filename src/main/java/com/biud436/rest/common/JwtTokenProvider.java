package com.biud436.rest.common;

import com.biud436.rest.domain.user.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("jwt.access-secret")
    private String secretKey;

    public static final Long MINUTE = 1000 * 60L;
    public static final Long HOUR = 1000 * 60L * 60L;

    private final long exp = HOUR * 2L;

    @PostConstruct
    protected void initialize() {
        secretKey = Base64.getEncoder().encodeToString(getBytesFromSecretKey());
    }

    private byte[] getBytesFromSecretKey() {
        return secretKey.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(UserInfoDto data, Authority authority) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", SignatureAlgorithm.HS256.getValue());

        Date iat = new Date();
        Date ext = new Date();
        ext.setTime(iat.getTime() + exp);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", data.getId());
        payload.put("role", authority.getValue());

        String jwt = Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(iat)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, getBytesFromSecretKey())
                .compact();

        return jwt;
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date iat = new Date();
        Date ext = new Date();
        ext.setTime(iat.getTime() + exp);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("roles", authorities)
                .claim("id", authentication.getName())
                .setIssuedAt(iat)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, getBytesFromSecretKey())
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public Optional<UserInfoDto> decode(String jwt) {

        UserInfoDto userInfo = null;

        try {
            Claims claims = Jwts.parser().setSigningKey(getBytesFromSecretKey())
                    .parseClaimsJws(jwt).getBody();

            ObjectMapper mapper = new ObjectMapper();
            userInfo = mapper.readValue(jwt, UserInfoDto.class);

        } catch (ExpiredJwtException e) {

        } catch (Exception e) {

        }

        return Optional.of(userInfo);
    }

    public boolean verifyJWT(String jwt) {
        boolean isVerify = false;

        // log
        System.out.println("verifyJWT : " + jwt);

        try {
            isVerify = getClaimsFromToken(jwt) != null;


        } catch (ExpiredJwtException e) {
            isVerify = false;
        } catch (Exception e) {
            e.printStackTrace();
            isVerify = false;
        }

        return isVerify;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getBytesFromSecretKey()).parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(
                claims.get("id", String.class),
                "",
                authorities
        );


        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}

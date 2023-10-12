package com.biud436.rest.common.security;

import com.biud436.rest.domain.user.dto.UserInfoDto;
import com.biud436.rest.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    public static final Long MINUTE = 60L;
    public static final Long HOUR = 60L * 60L;

    //    @Value("jwt.access-expiration")
    private final String accessExpiration = "14d";

    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void initialize() {
        secretKey = Base64.getEncoder().encodeToString(getBytesFromSecretKey());
    }

    private byte[] getBytesFromSecretKey() {
        return secretKey.getBytes(StandardCharsets.UTF_8);
    }

    public long convertTimeToLong(String time) {
        long result = 0L;
        time = time.trim();
        time = time.replaceAll("\\s", "");

        if (!Strings.hasText(time)) {
            time = time.replaceAll("[^0-9]", "");
            result = Long.parseLong(time);

            return result;
        }

        if (time.contains("m")) {
            result = Long.parseLong(time.replace("m", "")) * MINUTE;
        } else if (time.contains("h")) {
            result = Long.parseLong(time.replace("h", "")) * HOUR;
        } else if (time.contains("d")) {
            result = Long.parseLong(time.replace("d", "")) * HOUR * 24L;
        } else if (time.contains("w")) {
            result = Long.parseLong(time.replace("w", "")) * HOUR * 24L * 7L;
        } else if (time.contains("M")) {
            result = Long.parseLong(time.replace("M", "")) * HOUR * 24L * 30L;
        } else if (time.contains("y")) {
            result = Long.parseLong(time.replace("y", "")) * HOUR * 24L * 365L;
        }

        return result;
    }

    public String generateToken(UserInfoDto data, Authority authority) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", SignatureAlgorithm.HS256.getValue());

        Date iat = new Date();
        Date ext = new Date();

        long exp = convertTimeToLong(accessExpiration) * 1000L;
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
        long exp = convertTimeToLong(accessExpiration) * 1000L;
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

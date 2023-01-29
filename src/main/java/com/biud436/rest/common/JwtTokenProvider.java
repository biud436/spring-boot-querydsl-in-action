package com.biud436.rest.common;

import com.biud436.rest.domain.user.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("jwt.access-secret")
    private String secretKey;

    public static final Long MINUTE = 1000 * 60L;
    public static final Long HOUR = 1000 * 60L * 60L;

    private long exp = HOUR * 2L;

    @PostConstruct
    protected void initialize() {
        secretKey = Base64.getEncoder().encodeToString(getBytesFromSecretKey());
    }

    private byte[] getBytesFromSecretKey() {
        return secretKey.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(UserInfoDto data, List<String> roles) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", SignatureAlgorithm.HS256.getValue());

        Date iat = new Date();
        Date ext = new Date();
        ext.setTime(iat.getTime() + exp);

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", data.getId());

        String jwt = Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(iat)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwt;
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
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getBytesFromSecretKey())
                    .parseClaimsJws(jwt);
            isVerify = !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            isVerify = false;
        } catch (Exception e) {
            isVerify = false;
        }

        return isVerify;
    }
}

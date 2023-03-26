package com.biud436.rest.common;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = resolveToken(request);
            boolean isValidToken = StringUtils.hasText(jwt) &&
                    jwtTokenProvider.verifyJWT(jwt);

            if (isValidToken) {
                // JWT 디코딩
                Claims claims = jwtTokenProvider.getClaimsFromToken(jwt);
                String role = claims.get("role", String.class);

                // role 값 검증
                if (StringUtils.hasText(role) && role.equals("ROLE_ADMIN")) {
                    // ROLE_ADMIN인 경우
                } else {
                    // 403 Forbidden 응답 반환
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                    return;
                }
            }
        } catch (Exception ex) {
            // 예외 처리
            // ...
        }

        filterChain.doFilter(request, response);
    }
}

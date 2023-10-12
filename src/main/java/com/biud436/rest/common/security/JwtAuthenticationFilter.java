package com.biud436.rest.common.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        super();
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String token = resolveToken(request);
//            boolean isValidToken = StringUtils.hasText(token) &&
//                    jwtTokenProvider.verifyJWT(token);
//
//            if (isValidToken) {
//                // JWT 디코딩
//                Claims claims = jwtTokenProvider.getClaimsFromToken(token);
//                String role = claims.get("roles", String.class);
//                String id = claims.get("id", String.class);
//
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(
//                        authentication
//                );
//            }
//        } catch (Exception ex) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);
        boolean isValidToken = StringUtils.hasText(token) &&
                jwtTokenProvider.verifyJWT(token);

        if (isValidToken) {
            // JWT 디코딩
            Claims claims = jwtTokenProvider.getClaimsFromToken(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(
                    authentication
            );
        }

        chain.doFilter(request, response);
    }
}

package com.biud436.rest.common;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AspectProvider {


    @Around("@annotation(LogTime)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

    @Around("@annotation(UserOnly)")
    public Object userOnly(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        if (!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            throw new IllegalArgumentException("사용자만 접근 가능합니다.");
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

    // Parameter Annotation
    @Around("@annotation(UserInfo)")
    public Object getUserInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object proceed = joinPoint.proceed(new Object[]{userInfo});

        return proceed;
    }

}

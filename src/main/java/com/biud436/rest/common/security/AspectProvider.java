package com.biud436.rest.common.security;

import com.biud436.rest.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectProvider {

    private final UserService userService;

    @Around("@annotation(com.biud436.rest.common.annotation.LogTime)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

    @Around("execution( * com.biud436.rest.web.api.controller.ApiController.*(..))")
    public Object loggingTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();

        return new LoggingTimeProxyHandler(proceed).wrap();
    }

    @After("within(com.biud436.rest.web.api.controller.ApiController)")
    public void after() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            log.info("[{}] - {}이 호출되었습니다.", attributes.getRequest().getMethod(), attributes.getRequest().getRequestURI());
        }
    }

    @Around("@annotation(com.biud436.rest.common.annotation.UserOnly)")
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

}

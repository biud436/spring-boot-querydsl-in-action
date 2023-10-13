package com.biud436.rest.common.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
public class LoggingTimeProxyHandler implements MethodInterceptor {

    private final Object targetController;

    @Nullable
    @Override
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = invocation.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(invocation.getMethod() + " executed in " + executionTime + "ms");

        return proceed;
    }

    public Object wrap() {
        final ProxyFactory proxyFactory = new ProxyFactory(targetController);

        proxyFactory.addAdvice(this);

        return proxyFactory.getProxy();
    }
}

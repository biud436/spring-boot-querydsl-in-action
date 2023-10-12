package com.biud436.rest.common.annotation;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * `@UserInfo`는 `@AuthenticationPrincipal`과 같은 역할을 합니다.
 * 이 파라미터 어노테이션의 구현은 {@link com.biud436.rest.common.resolver.UserInfoArgumentResolver}에 있습니다.
 * User 객체의 내용을 전부 채워서 반환합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Parameter(hidden = true)
public @interface UserInfo {
}
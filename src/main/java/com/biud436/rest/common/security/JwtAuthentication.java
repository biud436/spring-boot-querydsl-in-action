package com.biud436.rest.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthentication implements Authentication {

    private final String token;
    private final String id;
    private final String role;

    private boolean isAuthenticated;

    public JwtAuthentication(String token, String id, String role) {
        this.token = token;
        this.id = id;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAuthenticated) {
            return Collections.singletonList(new SimpleGrantedAuthority(role));
        }

        return Collections.singletonList(new SimpleGrantedAuthority(Authority.GUEST.getValue()));
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return id;
    }
}
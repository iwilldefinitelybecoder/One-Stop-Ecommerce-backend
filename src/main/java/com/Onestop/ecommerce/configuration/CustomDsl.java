package com.Onestop.ecommerce.configuration;

import com.Onestop.ecommerce.jwtfilter.filter.CustomAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager);
    }

    public static CustomDsl customDsl() {
        return new CustomDsl();
    }
}

package com.example.edo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class PublicPathsConfig {

    @Bean
    public RequestMatcher publicPaths() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/registration"),
                new AntPathRequestMatcher("/static/**"),
                new AntPathRequestMatcher("/forgotPassword"),
                new AntPathRequestMatcher("/checkCode"),
                new AntPathRequestMatcher("/resetPassword")
        );
    }
}

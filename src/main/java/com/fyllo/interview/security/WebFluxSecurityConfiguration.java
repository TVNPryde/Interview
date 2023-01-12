package com.fyllo.interview.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfiguration {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Thanh")
                .password("Fyllo")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}

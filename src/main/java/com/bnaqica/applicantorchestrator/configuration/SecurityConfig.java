package com.bnaqica.applicantorchestrator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationConverter customJwtAuthenticationConverter;
    @Value("#{'${applicant-orchestrator.allowed.origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${applicant-orchestrator.allowed.headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${applicant-orchestrator.allowed.methods}'.split(',')}")
    private List<String> allowedMethods;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/api-docs").permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(customJwtAuthenticationConverter);
        http.cors().configurationSource(createCorsConfiguration());

        return http.build();
    }

    private CorsConfigurationSource createCorsConfiguration() {
        return request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(allowedOrigins);
            corsConfig.setAllowedHeaders(allowedHeaders);
            corsConfig.setAllowedMethods(allowedMethods);
            return corsConfig;
        };
    }
}

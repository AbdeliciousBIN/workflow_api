package com.i2s.worfklow_api_final.configuration;


import com.i2s.worfklow_api_final.security.CustomUserDetailsService;
import com.i2s.worfklow_api_final.security.JwtAuthenticationFilter;
import com.i2s.worfklow_api_final.security.JwtTokenProvider;
import com.i2s.worfklow_api_final.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private final RestAuthenticationEntryPoint unauthorizedHandler;


    @Autowired
    public SecurityConfig(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService, RestAuthenticationEntryPoint unauthorizedHandler) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;

    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService);

    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll() // allow unauthenticated access to the login endpoint
                .antMatchers("/api/public/**").permitAll() // allow unauthenticated access to public endpoints

                // secure specific endpoints with roles
                .antMatchers("/api/admin/**").hasRole("ADMIN") // only users with ADMIN role can access /api/admin endpoints
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // users with USER or ADMIN role can access /api/user endpoints

                .anyRequest().authenticated() // secure all other routes
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // allow all origins
        configuration.setAllowedMethods(Collections.singletonList("*")); // allow all methods
        configuration.setAllowedHeaders(Collections.singletonList("*")); // allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

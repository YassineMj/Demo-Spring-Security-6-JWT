package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Autowired
	 private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer->customizer.disable()); //disable CSRF token
        http.authorizeHttpRequests(request->request
        		.requestMatchers("api/user/accounts/register","api/user/accounts/login").permitAll()
        		.requestMatchers("api/user/accounts/get-all-accounts").hasAnyRole("USER","ADMIN")
        		.requestMatchers("api/user/accounts/add-role-to-user").hasRole("ADMIN")
        		.requestMatchers("api/user/accounts/add-role").hasRole("ADMIN")
        		.requestMatchers("/api/student/add-student").hasRole("ADMIN")
        		.requestMatchers("/api/student/get-all-students").hasAnyRole("USER","ADMIN")
        		.requestMatchers("/api/student/get-student/**").hasAnyRole("USER","ADMIN")
        		.requestMatchers("/api/student/update-student/**").hasRole("ADMIN")
        		.requestMatchers("/api/student/delete-student/**").hasRole("ADMIN")

        		.anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults()); // solution de eror403
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
    	provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    	provider.setUserDetailsService(userDetailsService);
    	return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
}

package com.example.haircut.security;

import javax.crypto.SecretKey;

import com.example.haircut.repository.EmployeeRepository;
import com.example.haircut.security.authentication.EmployeeService;
import com.example.haircut.security.jwt.JWTConfig;
import com.example.haircut.security.jwt.JWTSecretKey;
import com.example.haircut.security.jwt.JWTTokenVerify;
import com.example.haircut.security.jwt.JWTUserEmailPasswordFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeService userDetailService;
    private final SecretKey secretKey;
    private final JWTConfig jwtConfig;

    @Autowired
    public WebSecurityConfig(EmployeeService userDetailService, SecretKey secretKey, JWTConfig jwtConfig) {
        this.userDetailService = userDetailService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(new JWTUserEmailPasswordFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JWTTokenVerify(secretKey, jwtConfig), JWTUserEmailPasswordFilter.class)
                .authorizeRequests()
                .antMatchers("/api/empLogin","/api/customerLogin","/api/addNewCustomer","/api/checkCode","/api/sendEmail","/swagger-ui/**")
                .permitAll().anyRequest()
                .authenticated();
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}

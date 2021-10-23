package com.example.haircut.security.jwt;

import com.example.haircut.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import io.jsonwebtoken.Jwts;

public class JWTUserEmailPasswordFilter extends UsernamePasswordAuthenticationFilter{
    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;
    private final JWTSecretKey secretKey;

    public JWTUserEmailPasswordFilter(AuthenticationManager authenticationManager, JWTConfig jwtConfig, JWTSecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // get username and password from request
            LoginDTO user = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginDTO.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword()
            );

            // check whether username and password are valid or not;
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        } catch (AuthenticationException | IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    // just call as user is valid
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                //     .setPayload(((UserDetail)authResult.getPrincipal()).getUsername())
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);


    }
}

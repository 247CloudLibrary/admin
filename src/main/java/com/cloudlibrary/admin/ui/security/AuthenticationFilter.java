package com.cloudlibrary.admin.ui.security;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudlibrary.admin.application.service.AdminReadUseCase;
import com.cloudlibrary.admin.application.service.AdminService;
import com.cloudlibrary.admin.ui.requestBody.AdminLoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AdminService adminService;
    Environment env;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, AdminService adminService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/v1/admin/signin");
        this.adminService = adminService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AdminLoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), AdminLoginRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    creds.getId(), creds.getPw(), new ArrayList<>());
            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        AdminReadUseCase.FindAdminResult findAdminResult = adminService.getAdminById(userName);

        String token = Jwts.builder()
                .setSubject(findAdminResult.getId())
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        Cookie setCookie = new Cookie("token", token);
        response.addCookie(setCookie);
        response.addHeader("token", token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String result = new ObjectMapper().writeValueAsString(findAdminResult);
        response.getWriter().write(result);
    }
}

package com.cloudlibrary.admin.ui.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("deprecation")
public class TokenValidationInterceptor extends HandlerInterceptorAdapter {

    Environment env;

    public TokenValidationInterceptor(Environment env) {
        this.env = env;
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty())
            returnValue = false;

        return returnValue;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new AccessDeniedException("인증 정보 누락");
        }

        String jwt = authorizationHeader.replace("Bearer", "").trim();
        if (!isJwtValid(jwt)) {
            throw new AccessDeniedException("인증 오류");
        }

        return super.preHandle(request, response, handler);
    }
}

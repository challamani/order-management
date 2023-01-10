package com.restaurant.dinehouse.config;

import com.restaurant.dinehouse.exception.BadRequestException;
import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.model.UserSession;
import com.restaurant.dinehouse.repository.SessionRepository;
import com.restaurant.dinehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(1)
@ConditionalOnProperty(
        value="system.security.authorization.enable",
        havingValue = "true",
        matchIfMissing = true)
public class CustomFilter implements Filter {

    private final String headerKey = "Authorization";
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpRequest =  (HttpServletRequest)request;

        String path = httpRequest.getRequestURI();
        log.info("path {}",path);

        if(path.contains("login") || path.contains("swagger-ui") || path.contains("api-docs")){
            chain.doFilter(request, response);
            return;
        }

        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpRequest.getHeader(key);
            log.info("header key :: {} value :: {}",key,value);
        }

        String headerValue = httpRequest.getHeader(headerKey);
        if(StringUtils.isBlank(headerValue)){
            throw new BadRequestException("Authorization header is mandatory!");
        }
        log.info("Authorization Header {}", headerValue);

        String token = headerValue.split(" ")[1];
        UserSession userSession = sessionRepository.findByToken(token.trim());
        log.info("token expireOn {} currentDate {}", userSession.getExpireOn(), Calendar.getInstance().getTime());
        if (userSession.getExpireOn().compareTo(Calendar.getInstance().getTime()) > 0) {
            populateUser(userSession.getUserId());
        } else {
            throw new BadRequestException("invalid token or token expired");
        }
        chain.doFilter(request, response);
    }

    @Bean
    @RequestScope
    public User populateUser(@NotNull String userId){
        return userRepository.findByUserId(userId);
    }
}

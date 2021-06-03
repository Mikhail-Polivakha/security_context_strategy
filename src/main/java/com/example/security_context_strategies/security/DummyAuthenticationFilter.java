package com.example.security_context_strategies.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class DummyAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (isNull(authorizationHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            beginAuthenticationProcess(request, response, filterChain, authorizationHeader);
        }
    }

    private void beginAuthenticationProcess(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authorizationHeader) throws IOException, ServletException {
        final String decodedCredentialsString = new String(Base64.getDecoder().decode(authorizationHeader.substring(6).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final String[] credentials = decodedCredentialsString.split(":");
        final String username = credentials[0];
        final String password = credentials[1];
        if (username.equals("username") && password.equals("password")) {
            System.out.format("Thread [%s] persisting an Authentication object into SecurityContext\n", Thread.currentThread().getName());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password, List.of(() -> "AUTHORITY")));
            filterChain.doFilter(request, response);
        } else {
            interruptRequestDueToInvalidCredentails(response);
        }
    }

    private void interruptRequestDueToInvalidCredentails(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
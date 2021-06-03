package com.example.security_context_strategies.resources;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RestAPIResource {

    @GetMapping(path = "/hello")
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        final Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        final String result = String.format("Thread [%s] tries to access Authentication object in Security Context. " +
                                                    "For this Thread SecurityContext is present: [%s]\n",
                                            Thread.currentThread().getName(),
                                            authentication.isPresent());
        System.out.println(result);
        return result;
    }
}
package com.gestion.tache.security

import groovy.transform.CompileStatic
import org.springframework.web.filter.OncePerRequestFilter

import javax.annotation.Priority
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@CompileStatic
@Priority(Integer.MIN_VALUE)
class CorsFilter extends OncePerRequestFilter {

    CorsFilter() {}
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
            String origin = request.getHeader("Origin");
             boolean options = "OPTIONS" == request.getMethod();

        if (options) {
            if (origin == null) return;
            response.addHeader("Access-Control-Allow-Headers", "origin, authorization, accept, content-type, x-requested-with");
            response.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");
            response.addHeader("Access-Control-Max-Age", "3600");
        }

        response.addHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if (!options) filterChain.doFilter(request, response);


    }
}

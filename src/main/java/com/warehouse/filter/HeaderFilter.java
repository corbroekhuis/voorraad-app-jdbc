package com.warehouse.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class HeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String value = request.getHeader(headerName);
            System.out.println( headerName + ": " + value);
        }
        filterChain.doFilter(request, response);
    }
}

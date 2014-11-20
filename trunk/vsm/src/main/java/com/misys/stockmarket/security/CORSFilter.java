package com.misys.stockmarket.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CORSFilter extends OncePerRequestFilter {

	private static final Log LOG = LogFactory
			.getLog(CORSFilter.class);
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    	response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Max-Age", "10");
        response.addHeader("Access-Control-Allow-Headers", "Set-Cookie, GET, PUT, POST, null, X-XSRF-Token, X-CSRF-TOKEN, origin, content-type, accept, X-CSRF-PARAM, X-CSRF-HEADER");
        response.addHeader("Access-Control-Expose-Headers", "X-CSRF-TOKEN, X-CSRF-HEADER");
        
        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
            	LOG.error(e);
            }
        } else{
            filterChain.doFilter(request, response);
        }
    }
 }

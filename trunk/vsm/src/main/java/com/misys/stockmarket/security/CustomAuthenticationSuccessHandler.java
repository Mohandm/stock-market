package com.misys.stockmarket.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	 public CustomAuthenticationSuccessHandler() {
	       super();
	       setRedirectStrategy(new NoRedirectStrategy());
	   }

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {

	           super.onAuthenticationSuccess(request, response, authentication);
				ObjectMapper mapper = new ObjectMapper();
				LoginResponse loginResponse = new LoginResponse();
	           loginResponse.setEmail(authentication.getName());
	           response.getWriter().print(mapper.writeValueAsString(loginResponse));
	           response.getWriter().flush();
	    }


	    protected class NoRedirectStrategy implements RedirectStrategy {

	        @Override
	        public void sendRedirect(HttpServletRequest request,
	                HttpServletResponse response, String url) throws IOException {
	            // no redirect

	        }

	    }
}

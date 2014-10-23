package com.misys.stockmarket.security;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String userName = (String) request
				.getAttribute(SPRING_SECURITY_FORM_USERNAME_KEY);
		request.removeAttribute(SPRING_SECURITY_FORM_USERNAME_KEY);
		return userName;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = (String) request
				.getAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY);
		request.removeAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY);
		return password;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/*
			 * HttpServletRequest can be read only once
			 */
			StringBuffer sb = new StringBuffer();
			String line = null;

			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			// json transformation
			ObjectMapper mapper = new ObjectMapper();
			LoginRequest loginRequest = mapper.readValue(sb.toString(),
					LoginRequest.class);
			request.setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY,
					loginRequest.getEmail());
			request.setAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY,
					loginRequest.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.attemptAuthentication(request, response);
	}
}

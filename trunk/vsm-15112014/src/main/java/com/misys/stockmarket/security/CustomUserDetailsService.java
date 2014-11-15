package com.misys.stockmarket.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.UserDAO;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Inject
	UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			UserMaster user = userDao.findByEmail(email);
			boolean userEnabled = IApplicationConstants.EMAIL_VERIFIED_YES
					.equals(user.getVerified());
			userDetails = new User(user.getEmail(), user.getPassword(),
					userEnabled, true, true, true,
					buildDefaultGrantedAuthority());
			return userDetails;
		} catch (DBRecordNotFoundException e) {
			throw new UsernameNotFoundException("Unable to retrieve user", e);
		}
	}

	private List<GrantedAuthority> buildDefaultGrantedAuthority() {
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		return grantedAuths;
	}

}

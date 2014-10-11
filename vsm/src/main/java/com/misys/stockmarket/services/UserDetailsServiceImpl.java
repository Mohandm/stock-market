package com.misys.stockmarket.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.UserDAO;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DBRecordNotFoundException;

@Service("userDetailsServiceImpl")
@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

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
			boolean credentialsNonExpired = !IApplicationConstants.USER_PASSWORD_EXPIRED
					.equals(user.getActive());
			boolean accountNonLocked = IApplicationConstants.USER_ACTIVATED
					.equals(user.getActive());
			userDetails = new User(user.getEmail(), user.getPassword(),
					userEnabled, true, credentialsNonExpired, accountNonLocked,
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

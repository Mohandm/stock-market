package com.misys.stockmarket.services;

import javax.inject.Inject;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.enums.VALIDATION_MODE;
import com.misys.stockmarket.exception.BaseException;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.services.email.EmailSenderService;
import com.misys.stockmarket.utility.EmailFormatter;
import com.misys.stockmarket.validator.UserValidator;

@Service("registrationService")
@Repository
public class RegistrationService {

	@Inject
	private UserService userService;

	@Inject
	private EmailSenderService emailSender;

	@Inject
	private UserValidator userValidator;

	@Inject
	private LeagueService leagueService;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Transactional(rollbackFor = DAOException.class)
	public void registerUser(UserFormBean userFormBean) throws BaseException {

		// LINK TO DEFAULT GROUP
		try {

			// VALIDATE USER MASTER DATA
			userValidator.isUserMasterDataValid(userFormBean,
					VALIDATION_MODE.SAVE, null);

			// EMAIL ADRESS OK TO REGISTER
			UserMaster userMaster = new UserMaster();
			userMaster.setFirstName(userFormBean.getFirstName());
			userMaster.setLastName(userFormBean.getLastName());
			userMaster.setEmail(userFormBean.getEmail());
			userMaster.setPassword(passwordEncoder.encode(userFormBean
					.getPassword()));
			userMaster.setVerified(IApplicationConstants.EMAIL_VERIFIED_NO);
			userMaster.setActive(IApplicationConstants.USER_DEACTIVATED);
			userService.saveUser(userMaster);

			leagueService.addUserToDefaultLeague(userMaster);

			// SEND ACTIVATION ACCOUNT EMAIL NOTIFICATION
			SimpleMailMessage message = EmailFormatter
					.generateActivationMessage(userMaster);

			emailSender.sendEmail(message);
		} catch (LeagueException e) {
			throw new BaseException(e);
		}

	}
}

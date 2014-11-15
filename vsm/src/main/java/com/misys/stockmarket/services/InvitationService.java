package com.misys.stockmarket.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.dao.InvitationDAO;
import com.misys.stockmarket.domain.entity.UserInvitation;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.DAOException;
import com.misys.stockmarket.exception.DBRecordNotFoundException;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.InvitationServiceException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.utility.SecurityUtil;

@Service("invitationService")
@Repository
public class InvitationService {

	private static final Log LOG = LogFactory.getLog(InvitationService.class);

	@Inject
	private InvitationDAO invitationDAO;

	@Inject
	private UserService userService;

	public void save(UserInvitation userInvitation)
			throws InvitationServiceException {
		try {
			invitationDAO.persist(userInvitation);
		} catch (DAOException e) {
			throw new InvitationServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	public void update(UserInvitation userInvitation)
			throws InvitationServiceException {
		try {
			invitationDAO.update(userInvitation);
		} catch (DAOException e) {
			throw new InvitationServiceException(e);
		}
	}

	@Transactional(rollbackFor = DAOException.class)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseMessage inviteUser(UserFormBean userFormBean) {

		// Validate already registered users
		String email = userFormBean.getEmail();
		try {
			userService.findByEmail(email);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"The account is already registered in the application.");

		} catch (EmailNotFoundException emailNotFoundException) {
			// Ignore exception
			LOG.debug("The email address is not registered");
		}

		// Validate previous requests from the same user
		UserMaster referer = null;
		try {
			referer = userService.getLoggedInUser();
			invitationDAO.findInvitation(email, referer);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"You have already sent a request to that user.");
		} catch (EmailNotFoundException emailNotFoundException) {
			// Ignore exception
			LOG.debug("The user is not logged in");
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while sending the invitation. Please try again");
		} catch (DBRecordNotFoundException dbRecordNotFoundException) {
			// Ignore exception
			LOG.debug("The logged in user has not sent an invtitaion till now to this user.");
		}

		UserInvitation userInvitation = new UserInvitation();
		userInvitation.setEmail(email);
		userInvitation.setReferer(referer);
		String token = SecurityUtil.autoGeneratePassword(20);
		userInvitation.setToken(SecurityUtil.encodeValue(token));
		userInvitation
				.setAccepted(IApplicationConstants.INVITATION_ACCEPTED_NO);
		try {
			save(userInvitation);
		} catch (InvitationServiceException e) {
			LOG.error("Error while saving user invitation", e);
			return new ResponseMessage(ResponseMessage.Type.danger,
					"There was a technical error while sending the invitation. Please try again");
		}

		return new ResponseMessage(ResponseMessage.Type.success,
				"An invitation has been sent to your friend. ");
	}

	public List<UserInvitation> findAllPendingUserInvitationEmailNotifications()
			throws InvitationServiceException {
		try {
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("accepted",
					IApplicationConstants.INVITATION_ACCEPTED_NO);
			return invitationDAO.findByFilter(UserInvitation.class, criteria);
		} catch (DAOException e) {
			throw new InvitationServiceException(e);
		}
	}

	public ResponseMessage acceptInvitation(String token) {

		UserInvitation invitation;
		try {
			invitation = invitationDAO.findInvitation(token);
			invitation
					.setAccepted(IApplicationConstants.INVITATION_ACCEPTED_YES);
			update(invitation);
		} catch (DBRecordNotFoundException e) {
			// Just log the errors
			LOG.error("Unable to find the invitation", e);
		} catch (InvitationServiceException e) {
			// Just log the errors
			LOG.error("Unable to update the invitation", e);
		}
		return new ResponseMessage(ResponseMessage.Type.success,
				"Thank you for visiting the game. Please register to play the game. ");
	}
}

package com.misys.stockmarket.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.misys.stockmarket.constants.IApplicationConstants; 
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.mbeans.UserProfileFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.UserService; 

/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class FileController {

	private static final Log LOG = LogFactory
			.getLog(FileController.class);
	
	@Inject
	UserService userService;
	
	@Inject
	ServletContext servletContext;
	
	@RequestMapping(value = "/registerUserProfilePic", method = RequestMethod.POST ) 
	@ResponseBody
    public String handleFormUpload(@RequestParam("userId") String userId,
        @RequestParam("file") MultipartFile file) {
		try {
			if (!file.isEmpty() && !userId.isEmpty()) 
			{
				byte[] bytes = file.getBytes();
				UserMaster user = userService.findById(new Long(userId));
				Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
				user.setProfilePicture(blob);
				userService.updateUser(user);
				
				return "Success";
			} else {
				return "Failure";
			}
		} catch (IOException e) {
			LOG.error(e);
			return "Failure";
		}
		catch (UserServiceException e) {
			LOG.error(e);
			return "Failure";
		}
		catch (SQLException e) {
			LOG.error(e);
			return "Failure";
		}
    }
	
	@RequestMapping(value = "/updateUserProfilePic", method = RequestMethod.POST ) 
	@ResponseBody
    public String updateUserProfilePic(@RequestParam("file") MultipartFile file) {
		try {
			if (!file.isEmpty()) 
			{
				byte[] bytes = file.getBytes();
				UserMaster user = userService.getLoggedInUser();
				Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
				user.setProfilePicture(blob);
				userService.updateUser(user);
				
				return "Success";
			} else {
				return "Failure";
			}
		} catch (IOException e) {
			return "Failure";
		}
		catch (EmailNotFoundException e) {
			return "Failure";
		}
		catch (SQLException e) {
			return "Failure";
		}catch (UserServiceException e) {
			return "Failure";
		}
    }
	
	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateuser(@RequestBody UserProfileFormBean userFormBean) {
		try{
			UserMaster user = userService.getLoggedInUser();
			Blob blob = user.getProfilePicture();
			if(blob != null)
			{
				byte[] buff = blob.getBytes(1,(int)blob.length());
				Blob blobTemp = new javax.sql.rowset.serial.SerialBlob(buff);
				user.setProfilePicture(blobTemp);
			}
			user.setFirstName(userFormBean.getFirstName());
			user.setLastName(userFormBean.getLastName());
			userService.updateUser(user);
		
			return new ResponseMessage(
				ResponseMessage.Type.success, "Your profile has been updated");
		}
		catch (EmailNotFoundException e) {
			return new ResponseMessage(
					ResponseMessage.Type.error, "Technical Error while updating your profile");
		}
		catch (UserServiceException e) {
			return new ResponseMessage(
					ResponseMessage.Type.error, "Technical Error while updating your profile");
		}
		catch (SQLException e) {
			return new ResponseMessage(
					ResponseMessage.Type.error, "Technical Error while updating your profile");
		}
	}
	
	
	
	@RequestMapping(value = "/profilePic", method =  { RequestMethod.GET,
			RequestMethod.POST }) 
	@ResponseBody
    public void  profilePic(@RequestParam("userId") String userId, HttpServletResponse response) {
		UserMaster user;
		try {
			user = userService.findById(new Long(userId));
			Blob blob = user.getProfilePicture();
			if(blob != null)
			{
				byte[] buff = blob.getBytes(1,(int)blob.length());
				response.setContentType("image/png");
			    response.getOutputStream().write(buff);
			}
			else
			{
				File imgPath;
				if(IApplicationConstants.GENDER_MALE.equalsIgnoreCase(user.getGender()))
				{
					imgPath = new File(servletContext.getRealPath("/app/images/male.jpg"));
				}
				else
				{
					imgPath = new File(servletContext.getRealPath("/app/images/female.jpg"));
				}
				
				FileInputStream fis = new FileInputStream(imgPath);
				byte[] b = new byte[(int) imgPath.length()];
				fis.read(b);
				
				response.setContentType("image/jpg");
			    response.getOutputStream().write(b);
			}
			
		} catch (UserServiceException e) {
			LOG.error(e);
		} catch (NumberFormatException e) {
			LOG.error(e);
		}
		catch (SQLException e) {
			LOG.error(e);
		}
		catch (IOException e) {
			LOG.error(e);
		}
    }
	
	@RequestMapping(value = "/userProfilePic", method =  { RequestMethod.GET,
			RequestMethod.POST }) 
	@ResponseBody
    public void  userProfilePic(HttpServletResponse response) {
		UserMaster user;
		try {
			
			user = userService.getLoggedInUser();
			Blob blob = user.getProfilePicture();
			if(blob != null)
			{
				byte[] buff = blob.getBytes(1,(int)blob.length());
				response.setContentType("image/png");
			    response.getOutputStream().write(buff);
			}
			else
			{
				File imgPath;
				if(IApplicationConstants.GENDER_MALE.equalsIgnoreCase(user.getGender()))
				{
					imgPath = new File(servletContext.getRealPath("/app/images/male.jpg"));
				}
				else
				{
					imgPath = new File(servletContext.getRealPath("/app/images/female.jpg"));
				}
				
				FileInputStream fis = new FileInputStream(imgPath);
				byte[] b = new byte[(int) imgPath.length()];
				fis.read(b);
				
				response.setContentType("image/jpg");
			    response.getOutputStream().write(b);
			}
			
		} catch (NumberFormatException e) {
			LOG.error(e);
		}
		catch (SQLException e) {
			LOG.error(e);
		}
		catch (IOException e) {
			LOG.error(e);
		}
		catch (EmailNotFoundException e) {
			LOG.error(e);
		}
    }
}

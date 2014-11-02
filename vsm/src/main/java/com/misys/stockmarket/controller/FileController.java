package com.misys.stockmarket.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.services.UserService;

/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class FileController {

	@Inject
	UserService userService;
	
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
			return "Failure";
		}
		catch (UserServiceException e) {
			return "Failure";
		}
		catch (SQLException e) {
			return "Failure";
		}
    }
}

package vsm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.exception.UserProfileValidationException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.services.RegistrationService;

public class UserRegisrationTest {

	public static void main(String[] args)
			throws UserProfileValidationException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		RegistrationService registrationService = (RegistrationService) applicationContext
				.getBean("registrationService");

		try {
			UserFormBean userFormBean = new UserFormBean();
			userFormBean.setFirstName("Fname");
			userFormBean.setEmail("ranju@misys.com");
			userFormBean.setPassword("password");
			userFormBean.setConfirmPassword("password");
			registrationService.registerUser(userFormBean);
		} catch (UserProfileValidationException e) {
		}
	}

}

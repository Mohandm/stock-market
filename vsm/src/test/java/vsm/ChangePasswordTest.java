package vsm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.exception.LoginException;
import com.misys.stockmarket.services.LoginService;

public class ChangePasswordTest {

	public static void main(String[] args) throws LoginException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		LoginService loginService = (LoginService) applicationContext
				.getBean("loginService");
		loginService.changePassword(1L, "password");
	}

}

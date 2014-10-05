package vsm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.InvalidPasswordException;
import com.misys.stockmarket.services.LoginService;

public class LoginServiceTest {

	public static void main(String[] args) throws EmailNotFoundException,
			InvalidPasswordException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		LoginService loginService = (LoginService) applicationContext
				.getBean("loginService");
		System.out.println(loginService.validateLogin("sam.sundar@misys.com",
				"password1"));
	}

}

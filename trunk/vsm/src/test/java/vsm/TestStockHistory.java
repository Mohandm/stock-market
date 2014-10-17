package vsm;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.domain.entity.StockHistory;
import com.misys.stockmarket.services.StockService;
import com.misys.stockmarket.utility.DateUtils;

public class TestStockHistory {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		StockService stockService = (StockService) applicationContext
				.getBean("stockService");

		List<StockHistory> stockHistoryList = stockService.listStockHistory(
				"AAPL", DateUtils.getPastDateFromCurrentDate(6), new Date());
		System.out.println("HISTORY SIZE:" + stockHistoryList.size());
		for (StockHistory stockHistory : stockHistoryList) {
			System.out.println(stockHistory);
		}
	}

}

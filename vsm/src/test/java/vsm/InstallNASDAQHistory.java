package vsm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.exception.FinancialServiceException;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.model.json.QuoteHistoryJSONModel;
import com.misys.stockmarket.services.IFinancialService;
import com.misys.stockmarket.services.StockService;
import com.misys.stockmarket.utility.DateUtils;

public class InstallNASDAQHistory {

	private static final Log LOG = LogFactory
			.getLog(InstallNASDAQHistory.class);

	public static void main(String as[]) throws UnsupportedEncodingException,
			FinancialServiceException, StockServiceException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		IFinancialService financialService = (IFinancialService) applicationContext
				.getBean("yahooFinancialService");

		StockService stockService = (StockService) applicationContext
				.getBean("stockService");

		String responseJSONString = financialService.getStockHistory(
				stockService.listAllActiveStockSymbols(),
				DateUtils.getPastMonthFromCurrentDate(6), new Date());
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readValue(responseJSONString,
					JsonNode.class);
			JsonNode quoteArray = rootNode.findValue("quote");
			List<QuoteHistoryJSONModel> quoteHistoryJSONModels = mapper
					.readValue(
							quoteArray,
							mapper.getTypeFactory().constructCollectionType(
									List.class, QuoteHistoryJSONModel.class));
			stockService.saveStockHistoryList(quoteHistoryJSONModels);
		} catch (JsonParseException e) {
			LOG.error("Error while installing history stock data", e);
		} catch (JsonMappingException e) {
			LOG.error("Error while installing history stock data", e);
		} catch (IOException e) {
			LOG.error("Error while installing history stock data", e);
		}

	}
}

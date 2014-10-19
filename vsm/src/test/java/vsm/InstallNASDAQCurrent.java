package vsm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.misys.stockmarket.model.json.QuoteCurrentJSONModel;
import com.misys.stockmarket.services.IFinancialService;
import com.misys.stockmarket.services.StockService;

public class InstallNASDAQCurrent {

	private static final Log LOG = LogFactory
			.getLog(InstallNASDAQCurrent.class);

	public static void main(String as[]) throws UnsupportedEncodingException,
			FinancialServiceException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		IFinancialService financialService = (IFinancialService) applicationContext
				.getBean("yahooFinancialService");

		StockService stockService = (StockService) applicationContext
				.getBean("stockService");

		String responseJSONString = financialService.getStockCurrent(stockService.listAllActiveStockSymbols());
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readValue(responseJSONString,
					JsonNode.class);
			JsonNode quoteArray = rootNode.findValue("quote");
			List<QuoteCurrentJSONModel> quoteCurrentJSONModels = mapper
					.readValue(
							quoteArray,
							mapper.getTypeFactory().constructCollectionType(
									List.class, QuoteCurrentJSONModel.class));
			stockService.updateStockCurrent(quoteCurrentJSONModels);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

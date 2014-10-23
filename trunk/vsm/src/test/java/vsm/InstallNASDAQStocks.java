package vsm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.StockMaster;
import com.misys.stockmarket.exception.service.StockServiceException;
import com.misys.stockmarket.model.json.StockJSONModel;
import com.misys.stockmarket.services.StockService;

public class InstallNASDAQStocks {

	private static final Log LOG = LogFactory.getLog(InstallNASDAQStocks.class);

	public static void main(String as[]) throws StockServiceException {

		String fileName = "D:\\portalplatform\\STSWORKSPACE_FEB\\vsm\\src\\test\\resources\\nasdaq100_stock_list.json";

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF\\spring\\applicationContext.xml");
		StockService stockService = (StockService) applicationContext
				.getBean("stockService");
		List<StockMaster> activeStockList = stockService.listAllActiveStocks();
		List<StockMaster> inActiveStockList = stockService
				.listAllInActiveStocks();

		List<StockMaster> newStocksList = new ArrayList<StockMaster>();
		List<StockMaster> updateStocksList = new ArrayList<StockMaster>();

		File stockJsonFile = new File(fileName);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<StockJSONModel> stockJSONModels = mapper.readValue(
					stockJsonFile,
					mapper.getTypeFactory().constructCollectionType(List.class,
							StockJSONModel.class));
			for (StockJSONModel stockJSONModel : stockJSONModels) {
				StockMaster stockMaster = new StockMaster();
				stockMaster.setTikerSymbol(stockJSONModel.Symbol);
				stockMaster.setName(stockJSONModel.Name);
				if (!activeStockList.contains(stockMaster)) {
					if (inActiveStockList.contains(stockMaster)) {
						StockMaster stockMasterInactive = getStockMasterBySymbol(
								inActiveStockList, stockMaster.getTikerSymbol());
						stockMasterInactive
								.setActive(IApplicationConstants.STOCK_ACTIVE);
						updateStocksList.add(stockMasterInactive);
					} else {
						stockMaster
								.setActive(IApplicationConstants.STOCK_ACTIVE);
						newStocksList.add(stockMaster);
					}
				}
			}
			LOG.info("NEW STOCKS SIZE:" + newStocksList.size());
			LOG.info("UPDATE STOCK SIZE:" + updateStocksList.size());

			stockService.saveStocks(newStocksList);
			stockService.updateStocks(updateStocksList);
		} catch (JsonParseException e) {
			LOG.error(e);
		} catch (JsonMappingException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	private static StockMaster getStockMasterBySymbol(
			List<StockMaster> stockList, String tickerSymbol) {
		for (StockMaster stockMaster : stockList) {
			if (stockMaster.getTikerSymbol().equals(tickerSymbol)) {
				return stockMaster;
			}
		}
		return null;
	}
}

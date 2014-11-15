package com.misys.stockmarket.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteCurrentJSONModel {

	public String symbol;
	public String Currency;
	public String LastTradeDate;
	public String LastTradePriceOnly;
	public String DaysRange;
	public String YearRange;
	public String Open;
	public String PreviousClose;
	public String ChangeinPercent;
	public String Change;
	public String LastTradeTime;
	public String Volume;
}

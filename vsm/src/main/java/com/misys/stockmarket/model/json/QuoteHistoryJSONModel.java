package com.misys.stockmarket.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteHistoryJSONModel {

	public String Symbol;
	public String Date;
	public String Open;
	public String High;
	public String Low;
	public String Close;
	public String Volume;
	public String Adj_Close;
}

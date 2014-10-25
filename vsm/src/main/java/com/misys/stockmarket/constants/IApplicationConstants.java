package com.misys.stockmarket.constants;

import java.math.BigDecimal;

public interface IApplicationConstants {

	public final String EMAIL_VERIFIED_YES = "Y";

	public final String EMAIL_VERIFIED_NO = "N";

	public final String USER_ACTIVATED = "A";

	public final String USER_DEACTIVATED = "D";

	public final String USER_PASSWORD_EXPIRED = "E";

	public final String STOCK_ACTIVE = "A";

	public final String STOCK_INACTIVE = "I";

	public final String BUY_TYPE = "B";

	public final String SELL_TYPE = "S";

	public final String ORDER_PRICE_TYPE_MARKET = "01";

	public final String ORDER_PRICE_TYPE_LIMIT = "02";

	public final String ORDER_PRICE_TYPE_STOPLOSS = "03";

	public final String ORDER_INTRADAY_YES = "Y";

	public final String ORDER_INTRADAY_NO = "N";

	public final String ORDER_STATUS_PENDING = "P";

	public final String ORDER_STATUS_COMPLETED = "C";

	public final String ORDER_STATUS_INSUFFICIENT_FUNDS = "I";

	public final String LEAGUE_DURATION_DAILY = "D";

	public final String LEAGUE_DURATION_WEEKLY = "W";

	public final String LEAGUE_DURATION_FORTNIGHTLY = "F";

	public final String LEAGUE_DURATION_MONTHLY = "M";

	public final String LEAGUE_DURATION_YEARLY = "Y";

	public final String LEAGUE_STATUS_ACTIVE = "A";

	public final String LEAGUE_STATUS_INACTIVE = "I";

	public final String LEAGUE_STATUS_COMPLETED = "C";

	public final String DEFAULT_LEAGUE_NAME = "Global";
	
	public final String PREMIER_LEAGUE_NAME = "Premier";
	
	public final String CHAMPIONS_LEAGUE_NAME = "Champions";
	
	public final String LEGENDS_LEAGUE_NAME = "Legends";

	public final String WATCH_STOCK_STATUS_PENDING = "P";

	public final String WATCH_STOCK_STATUS_COMPLETED = "C";

	public final BigDecimal DEFAULT_LEAGUE_START_AMOUNT = BigDecimal
			.valueOf(1000000);

	public final BigDecimal DEFAULT_LEAGUE_QUALIFYING_AMOUNT = BigDecimal
			.valueOf(750000);
}

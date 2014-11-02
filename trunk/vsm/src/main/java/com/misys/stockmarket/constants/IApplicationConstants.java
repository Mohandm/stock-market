package com.misys.stockmarket.constants;

import java.math.BigDecimal;

public class IApplicationConstants {

	public static final String EMAIL_VERIFIED_YES = "Y";

	public static final String EMAIL_VERIFIED_NO = "N";

	public static final String USER_ACTIVATED = "A";

	public static final String USER_DEACTIVATED = "D";

	public static final String USER_EMAIL_SENT = "S";

	public static final String USER_PASSWORD_EXPIRED = "E";

	public static final String STOCK_ACTIVE = "A";

	public static final String STOCK_INACTIVE = "I";

	public static final String BUY_TYPE = "B";

	public static final String SELL_TYPE = "S";

	public static final String ORDER_PRICE_TYPE_MARKET = "01";

	public static final String ORDER_PRICE_TYPE_LIMIT = "02";

	public static final String ORDER_PRICE_TYPE_STOPLOSS = "03";

	public static final String ORDER_INTRADAY_YES = "Y";

	public static final String ORDER_INTRADAY_NO = "N";

	public static final String ORDER_STATUS_PENDING = "P";

	public static final String ORDER_STATUS_COMPLETED = "C";

	public static final String ORDER_STATUS_INSUFFICIENT_FUNDS = "I";

	public static final String LEAGUE_DURATION_DAILY = "D";

	public static final String LEAGUE_DURATION_WEEKLY = "W";

	public static final String LEAGUE_DURATION_FORTNIGHTLY = "F";

	public static final String LEAGUE_DURATION_MONTHLY = "M";

	public static final String LEAGUE_DURATION_YEARLY = "Y";

	public static final String LEAGUE_STATUS_ACTIVE = "A";

	public static final String LEAGUE_STATUS_INACTIVE = "I";

	public static final String LEAGUE_STATUS_COMPLETED = "C";

	public static final String DEFAULT_LEAGUE_NAME = "Global";

	public static final String PREMIER_LEAGUE_NAME = "Premier";

	public static final String CHAMPIONS_LEAGUE_NAME = "Champions";

	public static final String LEGENDS_LEAGUE_NAME = "Legends";

	public static final String WATCH_STOCK_STATUS_PENDING = "P";

	public static final String WATCH_STOCK_STATUS_COMPLETED = "C";

	public static final String FOLLOWER_STATUS_PENDING = "P";

	public static final String FOLLOWER_STATUS_ACCEPTED = "A";

	public static final String FOLLOWER_STATUS_REJECTED = "R";

	public static final String ALERT_TYPE_WATCH_LIST = "W";

	public static final String ALERT_TYPE_NOTIFICATIONS = "N";
	
	public static final String GENDER_MALE = "M";

	public static final String GENDER_FEMALE = "F";

	public static final BigDecimal DEFAULT_LEAGUE_START_AMOUNT = BigDecimal
			.valueOf(100000);

	public static final BigDecimal DEFAULT_LEAGUE_QUALIFYING_AMOUNT = BigDecimal
			.valueOf(75000);
}

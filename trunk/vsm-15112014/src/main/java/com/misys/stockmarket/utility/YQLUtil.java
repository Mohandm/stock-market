package com.misys.stockmarket.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.misys.stockmarket.exception.YQLException;

/**
 * @author sam sundar K
 * 
 */
public class YQLUtil {

	public final static String YQL_DATE_FORMAT = "yyyy-MM-dd";

	public static final Log LOG = LogFactory.getLog(YQLUtil.class);

	public static String executeQuery(String request) throws YQLException {
		StringBuffer responseBuffer = new StringBuffer();
		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(request);
			// Send GET request
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				LOG.error("Method failed: " + method.getStatusLine());
			}
			InputStream rstream = null;
			// Get the response body
			rstream = method.getResponseBodyAsStream();
			// Process the response from Yahoo! Web Services
			BufferedReader br = new BufferedReader(new InputStreamReader(
					rstream));
			String line;
			while ((line = br.readLine()) != null) {
				responseBuffer.append(line);
			}
			br.close();
		} catch (Exception e) {
			throw new YQLException(e);
		}
		return responseBuffer.toString();
	}

	public static String getCommaSeperatedQuoteSymbols(
			List<String> quoteSymbolList) {
		StringBuffer commaSeperatedQuotesBuffer = new StringBuffer();
		int i = 0;
		for (String quoteSymobol : quoteSymbolList) {
			commaSeperatedQuotesBuffer.append("\"").append(quoteSymobol)
					.append("\"");
			if (i < (quoteSymbolList.size() - 1)) {
				commaSeperatedQuotesBuffer.append(",");
			}
			i++;
		}
		return commaSeperatedQuotesBuffer.toString();
	}

	public static String uRLEncode(String decodedValue) throws YQLException {
		try {
			return URLEncoder.encode(decodedValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new YQLException(e);
		}
	}

	public static String dateToYQLStringDate(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat(YQL_DATE_FORMAT);
		return format.format(inputDate);
	}
}

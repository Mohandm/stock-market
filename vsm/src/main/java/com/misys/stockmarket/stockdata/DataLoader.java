package com.misys.stockmarket.stockdata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class DataLoader {
	
	public static void main(String[] args) throws Exception {
		//String request = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json";
        
		// Historical data
		String request = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20=%20%22YHOO%22%20and%20startDate%20=%20%222014-02-11%22%20and%20endDate%20=%20%222014-06-18%22&diagnostics=true&env=store://datatables.org/alltableswithkeys&format=json";
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(request);
        
        // Send GET request
        int statusCode = client.executeMethod(method);
        
        if (statusCode != HttpStatus.SC_OK) {
        	System.err.println("Method failed: " + method.getStatusLine());
        }
        InputStream rstream = null;
        
        // Get the response body
        rstream = method.getResponseBodyAsStream();
        
        // Process the response from Yahoo! Web Services
        BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
        String line;
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        br.close();
	}

}

package com.misys.stockmarket.security;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class JsonHtmlXssSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {
		if (value != null) {
			String encodedValue = Jsoup.clean (value, Whitelist.none());
			jsonGenerator.writeString(encodedValue);
		}
	}
}
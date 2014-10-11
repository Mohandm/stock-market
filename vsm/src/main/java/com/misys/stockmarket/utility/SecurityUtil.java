package com.misys.stockmarket.utility;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.RandomStringUtils;

public class SecurityUtil {

	/**
	 * Auto generate a random password of specified size
	 * 
	 * @param size
	 * @return
	 */
	public static String autoGeneratePassword(int size) {
		return RandomStringUtils.randomAlphanumeric(size);
	}

	/**
	 * Encode the value
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeValue(String value) {
		return DatatypeConverter.printBase64Binary(value.getBytes());
	}

	/**
	 * Decode the value
	 * 
	 * @param value
	 * @return
	 */
	public static String decodeValue(String value) {
		byte[] retr = DatatypeConverter.parseBase64Binary(value);
		return new String(retr);
	}

}

package com.ar.sgt.masterpromos.fcm.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static String getJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("{}", e);
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T fromJsonString(String value, Class<T> type) {
		try {
			return mapper.readValue(value, type);
		} catch (IOException e) {
			logger.error("{}", e);
			throw new RuntimeException(e);
		}
	}
	
}

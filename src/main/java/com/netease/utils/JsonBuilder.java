package com.netease.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBuilder {
	private Map<String, Object> response;
	
	public JsonBuilder() {
		response = new HashMap<String, Object>();
	}
	
	public JsonBuilder append(String key, Object value) {
		response.put(key, value);
		return this;
	}
	
	public String build() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// Swallow it.
		}
		return null;
	}
}

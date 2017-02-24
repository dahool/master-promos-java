package com.ar.sgt.masterpromos.model;

import java.io.Serializable;

public class Config extends ModelKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String configKey;
	
	private String configValue;

	public Config() { }
	
	public Config(String key) {
		setConfigKey(key);
	}
	
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
}

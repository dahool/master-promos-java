package com.ar.sgt.masterpromos.model;

import java.io.Serializable;

import com.ar.sgt.masterpromos.orm.annotation.Entity;

@Entity
public class Client extends ModelKey implements Serializable {

	transient private static final long serialVersionUID = 1L;
	
	private String deviceId;
	
	private String regId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("Client [");
		b.append("key=").append(getKey()).append(";");
		b.append("deviceId=").append(getDeviceId()).append(";");
		b.append("regId=").append(getRegId()).append("]");
		return b.toString();
	}
	
}

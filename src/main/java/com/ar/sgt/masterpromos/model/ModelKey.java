package com.ar.sgt.masterpromos.model;

import com.ar.sgt.masterpromos.orm.annotation.Id;

public abstract class ModelKey {

	@Id
	private Long key;
	
	public Long getKey() {
		return key;
	}
	
	public void setKey(Long key) {
		this.key = key;
	}
	
}

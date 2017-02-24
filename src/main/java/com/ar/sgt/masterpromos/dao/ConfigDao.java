package com.ar.sgt.masterpromos.dao;

import com.ar.sgt.masterpromos.model.Config;

public interface ConfigDao {

	Config getByKey(String key);
	
	void save(Config config);
	
}

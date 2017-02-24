package com.ar.sgt.masterpromos.dao.impl;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.ConfigDao;
import com.ar.sgt.masterpromos.model.Config;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Service
public class ConfigDaoImpl extends AbstractDao<Config> implements ConfigDao {

	protected ConfigDaoImpl() {
		super(Config.class);
	}

	@Override
	public Config getByKey(String key) {
		Query query = new Query(getEntityName());
		
		Filter filter = new Query.FilterPredicate("configKey", FilterOperator.EQUAL, key);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

}

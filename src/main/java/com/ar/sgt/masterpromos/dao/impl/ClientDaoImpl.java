package com.ar.sgt.masterpromos.dao.impl;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.ClientDao;
import com.ar.sgt.masterpromos.model.Client;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Service
public class ClientDaoImpl extends AbstractDao<Client> implements ClientDao {

	public ClientDaoImpl() {
		super(Client.class);
	}
	
	@Override
	public Client findByDeviceId(String deviceId) {
		Query query = new Query(getEntityName());
		
		Filter filter = new Query.FilterPredicate("deviceId", FilterOperator.EQUAL, deviceId);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

	@Override
	public Client findByRegId(String regId) {
		Query query = new Query(getEntityName());
		
		Filter filter = new Query.FilterPredicate("regId", FilterOperator.EQUAL, regId);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

}

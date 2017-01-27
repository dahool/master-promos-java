package com.ar.sgt.masterpromos.dao.impl;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.ClientDao;
import com.ar.sgt.masterpromos.model.Client;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Service
public class ClientDaoImpl extends AbstractDao<Client> implements ClientDao {

	private static final String ENTITY = "Client";

	public ClientDaoImpl() {
		super(ENTITY);
	}
	
	@Override
	protected Client fromEntity(Entity entity) {
		if (entity == null) return null;
		
		Client client = new Client();
		client.setKey(entity.getKey().getId());
		client.setDeviceId((String) entity.getProperty("deviceId"));
		client.setRegId((String) entity.getProperty("regId"));
		return client;
	}

	@Override
	protected Entity toEntity(Client client) {
		Entity entity = client.getKey() == null ? new Entity(ENTITY) : new Entity(KeyFactory.createKey(ENTITY, client.getKey()));
		entity.setProperty("deviceId", client.getDeviceId());
		entity.setProperty("regId", client.getRegId());
		return entity;
	}
	
	@Override
	public Client findByDeviceId(String deviceId) {
		Query query = new Query(ENTITY);
		
		Filter filter = new Query.FilterPredicate("deviceId", FilterOperator.EQUAL, deviceId);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

	@Override
	public Client findByRegId(String regId) {
		Query query = new Query(ENTITY);
		
		Filter filter = new Query.FilterPredicate("regId", FilterOperator.EQUAL, regId);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

}

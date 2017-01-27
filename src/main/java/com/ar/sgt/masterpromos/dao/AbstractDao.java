package com.ar.sgt.masterpromos.dao;

import java.util.ArrayList;
import java.util.List;

import com.ar.sgt.masterpromos.model.ModelKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class AbstractDao<T extends ModelKey> {

	private final String type;
	
	protected final DatastoreService datastoreService;
	
	protected AbstractDao(String type) {
		this.type = type;
		this.datastoreService = DatastoreServiceFactory.getDatastoreService();
	}
	
	protected abstract T fromEntity(Entity entity);
	
	protected abstract Entity toEntity(T obj);

	public void save(T obj) {
		Entity entity = toEntity(obj);
		datastoreService.put(entity);
		obj.setKey(entity.getKey().getId());
	}

	public List<T> listAll() {
		Query query = new Query(type);
		PreparedQuery pq = datastoreService.prepare(query);
		
		List<T> objects = new ArrayList<T>();
		
		for (Entity entity : pq.asIterable()) {
			objects.add(fromEntity(entity));
		}
		
		return objects;
	}

	public void delete(T obj) {
		if (obj != null && obj.getKey() != null) {
			datastoreService.delete(KeyFactory.createKey(type, obj.getKey()));
		}
	}
	
}

package com.ar.sgt.masterpromos.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ar.sgt.masterpromos.model.ModelKey;
import com.ar.sgt.masterpromos.model.Promo;
import com.ar.sgt.masterpromos.orm.annotation.EntityMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class AbstractDao<T extends ModelKey> {

	private final String entityName;
	
	private final Class<T> type;
	
	protected final DatastoreService datastoreService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EntityMapper mapper;
	
	protected AbstractDao(Class<T> type) {
		this.type = type;
		this.entityName = type.getSimpleName();
		this.datastoreService = DatastoreServiceFactory.getDatastoreService();
	}
	
	protected T fromEntity(Entity entity) {
		try {
			return mapper.fromDatastoreEntity(entity, type);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error("fromEntity: {}", e);
			throw new RuntimeException(e);
		}
	}

	protected Entity toEntity(T obj) {
		try {
			return mapper.toDatastoreEntity(obj);
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error("toEntity: {}", e);
			throw new RuntimeException(e);
		}
	}

	public void save(T obj) {
		Entity entity = toEntity(obj);
		datastoreService.put(entity);
		obj.setKey(entity.getKey().getId());
	}

	public List<T> listAll() {
		Query query = new Query(entityName);
		PreparedQuery pq = datastoreService.prepare(query);
		
		List<T> objects = new ArrayList<T>();
		
		for (Entity entity : pq.asIterable()) {
			objects.add(fromEntity(entity));
		}
		
		return objects;
	}

	public void delete(T obj) {
		if (obj != null && obj.getKey() != null) {
			datastoreService.delete(KeyFactory.createKey(entityName, obj.getKey()));
		}
	}
	
	protected String getEntityName() {
		return entityName;
	}
	
}

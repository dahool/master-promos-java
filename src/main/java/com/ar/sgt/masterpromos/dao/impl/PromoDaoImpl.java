package com.ar.sgt.masterpromos.dao.impl;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Service
public class PromoDaoImpl extends AbstractDao<Promo> implements PromoDao {

	private static final String ENTITY = "Promo";

	public PromoDaoImpl() {
		super(ENTITY);
	}

	@Override
	protected Promo fromEntity(Entity entity) {
		if (entity == null) return null;
		
		Promo promo = new Promo();
		promo.setKey(entity.getKey().getId());
		promo.setText((String) entity.getProperty("text"));
		promo.setImage((String) entity.getProperty("image"));
		promo.setUrl((String) entity.getProperty("url"));
		promo.setPoints((String) entity.getProperty("points"));
		promo.setPercentage((String) entity.getProperty("percentage"));
		return promo;
	}

	@Override
	protected Entity toEntity(Promo promo) {
		Entity entity = promo.getKey() == null ? new Entity(ENTITY) : new Entity(KeyFactory.createKey(ENTITY, promo.getKey()));
		entity.setProperty("text", promo.getText());
		entity.setUnindexedProperty("image", promo.getImage());
		entity.setUnindexedProperty("url", promo.getUrl());
		entity.setUnindexedProperty("points", promo.getPoints());
		entity.setUnindexedProperty("percentage", promo.getPercentage());
		return entity;
	}

	@Override
	public Promo findByText(String text) {
		Query query = new Query(ENTITY);
		
		Filter filter = new Query.FilterPredicate("text", FilterOperator.EQUAL, text);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}
		
}

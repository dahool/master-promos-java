package com.ar.sgt.masterpromos.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;
import com.ar.sgt.masterpromos.utils.DateUtils;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@Service
public class PromoDaoImpl extends AbstractDao<Promo> implements PromoDao {

	public PromoDaoImpl() {
		super(Promo.class);
	}

	@Override
	public Promo findByText(String text) {
		Query query = new Query(getEntityName());
		
		Filter filter = new Query.FilterPredicate("text", FilterOperator.EQUAL, text);
		query.setFilter(filter);
		
		PreparedQuery pq = datastoreService.prepare(query);
		return fromEntity(pq.asSingleEntity());
	}

	@Override
	public List<Promo> listExpired() {
		Query query = new Query(getEntityName());
		
		List<Filter> filters = new ArrayList<Query.Filter>();
		filters.add(new Query.FilterPredicate("expires", FilterOperator.LESS_THAN, DateUtils.getCurrent()));
		filters.add(new Query.FilterPredicate("expires", FilterOperator.NOT_EQUAL, null));
		
		query.setFilter(CompositeFilterOperator.and(filters));
		
		PreparedQuery pq = datastoreService.prepare(query);
		
		List<Promo> objects = new ArrayList<Promo>();
		
		for (Entity entity : pq.asIterable()) {
			objects.add(fromEntity(entity));
		}
		
		return objects;
	}

	@Override
	public List<Promo> listPromosOnly() {
		Query query = new Query(getEntityName());
		query.setFilter(new Query.FilterPredicate("expires", FilterOperator.EQUAL, null));
		
		PreparedQuery pq = datastoreService.prepare(query);
		
		List<Promo> objects = new ArrayList<Promo>();
		
		for (Entity entity : pq.asIterable()) {
			objects.add(fromEntity(entity));
		}
		
		return objects;
	}
	
	
		
}

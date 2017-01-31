package com.ar.sgt.masterpromos.dao.impl;

import org.springframework.stereotype.Service;

import com.ar.sgt.masterpromos.dao.AbstractDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
		
}

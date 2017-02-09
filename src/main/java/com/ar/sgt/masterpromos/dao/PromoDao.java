package com.ar.sgt.masterpromos.dao;

import java.util.List;

import com.ar.sgt.masterpromos.model.Promo;

public interface PromoDao {

	void save(Promo promo);

	List<Promo> listAll();

	void delete(Promo promo);
	
	Promo findByText(String text);
	
	List<Promo> listExpired();
	
	List<Promo> listPromosOnly();
	
}

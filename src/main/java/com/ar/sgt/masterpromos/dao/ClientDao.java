package com.ar.sgt.masterpromos.dao;

import java.util.List;

import com.ar.sgt.masterpromos.model.Client;

public interface ClientDao {

	void save(Client client);

	List<Client> listAll();

	void delete(Client client);
	
	Client findByDeviceId(String deviceId);
	
	Client findByRegId(String regId);
	
}

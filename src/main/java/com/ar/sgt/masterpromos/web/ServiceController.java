package com.ar.sgt.masterpromos.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ar.sgt.masterpromos.dao.ClientDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Client;
import com.ar.sgt.masterpromos.model.Promo;

@RestController
@RequestMapping("/app")
public class ServiceController {

	@Autowired
	private PromoDao promoDao;

	@Autowired
	private ClientDao clientDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/retrieve", method = RequestMethod.GET)
	public List<Promo> retrievePromos() {
		// delete expired if exists
		for (Promo p : promoDao.listExpired()) {
			promoDao.delete(p);
		}
		return promoDao.listAll();
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public void register(@RequestBody Client newClient) {
		Client c = clientDao.findByDeviceId(newClient.getDeviceId());
		
		if (c == null) {
			c = clientDao.findByRegId(newClient.getRegId());
			if (c == null) {
				c = new Client();
			}
			c.setDeviceId(newClient.getDeviceId());
		}
		
		c.setRegId(newClient.getRegId());
		clientDao.save(c);
		logger.debug("Registered {}", c);
		
	}
	
}

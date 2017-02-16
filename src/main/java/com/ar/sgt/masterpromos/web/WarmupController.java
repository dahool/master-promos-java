package com.ar.sgt.masterpromos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;

@Controller
@RequestMapping("/ha")
public class WarmupController {
	
	@Autowired
	private PromoDao promoDao;
	
	@RequestMapping(value = "/keepalive", produces = "text/plain")
	@ResponseBody
	public String warmpup() throws Exception {
		return "OK";
	}	

	@RequestMapping(value = "/cleanup", produces = "text/plain")
	@ResponseBody
	public String cleanExpired() {
		// delete expired if exists
		for (Promo p : promoDao.listExpired()) {
			promoDao.delete(p);
		}	
		return "OK";
	}
	
}

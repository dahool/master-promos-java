package com.ar.sgt.masterpromos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.sgt.masterpromos.dao.ConfigDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Config;
import com.ar.sgt.masterpromos.model.Promo;

@Controller
@RequestMapping("/ha")
public class WarmupController {
	
	@Autowired
	private PromoDao promoDao;

	@Autowired
	private ConfigDao configDao;

	@Value("${service.url}")
	private String url = null;
	
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
	
	@RequestMapping(value = "/init", produces = "text/plain")
	@ResponseBody
	public String init() {
		Config cfg = configDao.getByKey("URL");
		if (cfg == null) {
			cfg = new Config("URL");
			cfg.setConfigValue(url);
			configDao.save(cfg);
		}
		return "OK";
	}	
	
}

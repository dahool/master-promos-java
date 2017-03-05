package com.ar.sgt.masterpromos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.sgt.masterpromos.PromoParser;
import com.ar.sgt.masterpromos.dao.ConfigDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Config;
import com.ar.sgt.masterpromos.model.Promo;

@Controller
@RequestMapping("/ha")
public class WarmupController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PromoDao promoDao;

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private PromoParser parser;
	
	@Value("${service.url}")
	private String serviceUrl = null;

	@Value("${main.url}")
	private String mainUrl = null;
	
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
			cfg.setConfigValue(serviceUrl);
			configDao.save(cfg);
		}
		return "OK";
	}	

	@RequestMapping(value = "/urlcheck", produces = "text/plain")
	@ResponseBody
	public String urlcheck() throws Exception {

		String url = parser.serviceUrlCheck(mainUrl);
		
		logger.info("URL is: {}", url);
		
		Config cfg = configDao.getByKey("URL");
		
		if (url != null) {
			if (cfg == null) {
				cfg = new Config("URL");
				cfg.setConfigValue(url);
				configDao.save(cfg);
			} else if (!cfg.getConfigValue().equals(url)) {
				logger.warn("Updated service url: {}", url);
				cfg.setConfigValue(url);
				configDao.save(cfg);
			}
		} else {
			if (cfg != null && !mainUrl.equals(cfg.getConfigValue())) {
				cfg.setConfigValue(mainUrl);
				configDao.save(cfg);
			}
			throw new Exception("NULL URL!");
		}

		return "OK";
	}	

	
}

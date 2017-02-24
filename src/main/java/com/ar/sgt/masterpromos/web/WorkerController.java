package com.ar.sgt.masterpromos.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.sgt.masterpromos.NotificationService;
import com.ar.sgt.masterpromos.PromoParser;
import com.ar.sgt.masterpromos.dao.ConfigDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Config;
import com.ar.sgt.masterpromos.model.Promo;
import com.ar.sgt.masterpromos.utils.Equalator;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private PromoDao promoDao;

	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private PromoParser promoParser;

	@Autowired
	private ConfigDao configDao;
	
	@Value("${service.url}")
	private String url = null;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/single", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String updatePromos() throws Exception {
		
		try {
			runUpdatePromos();
		} catch (NoPromosException e) {
			// fail silently
		}
		
		return "OK";
	}
	
	@RequestMapping(value="/continue", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String updatePromosContinue() throws Exception {
		
		runUpdatePromos();
		
		return "OK";
	}
	
	private void runUpdatePromos() throws Exception {
		Config config = configDao.getByKey("URL");
		if (config != null) {
			url = config.getConfigValue();
		}

		List<Promo> foundPromos = promoParser.parse(url);
		List<Promo> existingDbPromos = promoDao.listPromosOnly();
		
		boolean isEmpty = foundPromos.isEmpty();
		
		boolean hasChanged = false;
		
		if (isEmpty && !existingDbPromos.isEmpty()) {
			
			promoDao.delete(existingDbPromos);
			hasChanged = true;
		
		} else if (!isEmpty) {
			
			List<Promo> deleteCandidates = new ArrayList<>();

			// check if any of the promos already exists in db. quick try by text
			for (Promo existingPromo : existingDbPromos) {
				Promo updated = findInList(foundPromos, existingPromo, Promo.TextEqualator);
				if (updated != null) {
					if (existingPromo.hasChanged(updated)) {
						hasChanged = true;
						existingPromo.copyFrom(updated);
						promoDao.save(existingPromo);
						logger.info("Updated: {}", existingPromo);
					}
					foundPromos.remove(updated);
				} else {
					logger.info("To be deleted: {}", existingPromo);
					deleteCandidates.add(existingPromo);
				}
			}

			if (!foundPromos.isEmpty()) {
				hasChanged = true;
				logger.info("Found: {}", foundPromos);
				for (Promo p : foundPromos) {
					// complete missing details
					Promo newPromo = promoParser.parseDetails(p);
					// now check again if exists
					Promo existingPromo = findInList(existingDbPromos, newPromo, Promo.TitleEqualator);
					if (existingPromo != null) {
						if (existingPromo.hasChanged(newPromo)) {
							existingPromo.copyFrom(newPromo);
							promoDao.save(existingPromo);
							logger.info("Updated: {}", existingPromo);
						}
						deleteCandidates.remove(existingPromo);
					} else {
						// new promo found, add it
						promoDao.save(newPromo);
						logger.info("Added: {}", newPromo);
					}
				}
			} else {
				logger.info("No changes.");
			}
			
			if (!deleteCandidates.isEmpty()) {
				hasChanged = true;
				promoDao.delete(deleteCandidates);
			}
			
		}

		if (hasChanged) {
			logger.debug("Send notification");
			notifyService.sendNotification();
		}
		
		if (isEmpty) {
			throw new NoPromosException();
		}
		
		logger.info("Worker end");
		
	}

	private Promo findInList(List<Promo> inList, Promo searchPromo, Equalator<Promo> comp) {
		for (Promo p : inList) {
			if (comp.equals(p, searchPromo)) {
				return p;
			}
		}
		return null;
	}

	@SuppressWarnings("serial")
	private static class NoPromosException extends Exception {
		
	}
	
}

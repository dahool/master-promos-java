package com.ar.sgt.masterpromos.web;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ar.sgt.masterpromos.NotificationService;
import com.ar.sgt.masterpromos.PromoParser;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private PromoDao promoDao;

	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private PromoParser promoParser;
	
	//@Value("${service.url}")
	private String url = "https://sorpresas.mastercard.com/ar/beneficios/main/index/1/10/op/key/descuentos_acumulables";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String updatePromos() throws Exception {
		List<Promo> promos = promoParser.parse(url);
		List<Promo> currentPromos = promoDao.listAll();

		boolean hasChanged = false;
		
		for (Promo cp : currentPromos) {
			if (evalCurrentPromos(promos, cp)) {
				hasChanged = true;
			}
		}

		if (!promos.isEmpty()) {
			hasChanged = true;
			logger.info("Found {}", promos);
			for (Promo p : promos) {
				promoDao.save(p);
			}
		}
		
		if (hasChanged) {
			logger.debug("Send notification");
			notifyService.sendNotification();
		}
		
		logger.info("Worker end");
		return "OK";
	}

	private boolean evalCurrentPromos(List<Promo> promos, final Promo cp) {
		ListIterator<Promo> it = promos.listIterator();
		while (it.hasNext()) {
			Promo p = it.next();
			// verificamos si ya existe la promo en la db
			if (cp.getText().equals(p.getText())) {
				// existe, la quitamos de la lista de promos encontradas, pero verificamos si cambio la imagen para actualizar el stock
				it.remove();
				if (!cp.getImage().equals(p.getImage())) {
					cp.setImage(p.getImage());
					promoDao.save(cp);
					return true;
				}
				return false;
			}
		}
		// la promo de la db no existe en las nuevas, la borramos y notificamos los cambios
		promoDao.delete(cp);
		return true;
	}

}

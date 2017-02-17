package com.ar.sgt.masterpromos.web;

import java.util.List;
import java.util.ListIterator;

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
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;
import com.ar.sgt.masterpromos.utils.DateUtils;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private PromoDao promoDao;

	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private PromoParser promoParser;
	
	@Value("${service.url}")
	private String url = null; //"https://sorpresas.mastercard.com/ar/beneficios/main/index/1/10/op/key/descuentos_acumulables";
	
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
		
		List<Promo> foundPromos = promoParser.parse(url);
		List<Promo> currentPromos = promoDao.listPromosOnly();
		
		boolean isEmpty = foundPromos.isEmpty();
		
		boolean hasChanged = false;
		
		for (Promo cp : currentPromos) {
			if (evalCurrentPromos(foundPromos, cp, new PromoTextEquals())) {
				hasChanged = true;
			}
		}

		if (!foundPromos.isEmpty()) {
			hasChanged = true;
			logger.info("Found {}", foundPromos);
			for (Promo p : foundPromos) {
				Promo newPromo = promoParser.parseDetails(p);
				// luego de completar los datos verificamos si realmente hubo cambios o sólo cambió el título
				Promo actual = findActual(currentPromos, newPromo, new PromoTitleEquals()); 
				if (actual == null) {
					// agregamos lo nuevo
					promoDao.save(newPromo);
				} else {
					// actualizamos los cambios y seguimos
					copyTo(newPromo, actual);
					promoDao.save(actual);
				}
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

	private void copyTo(final Promo fromElement, final Promo toElement) {
		toElement.setText(fromElement.getText());
		toElement.setHasStock(fromElement.getHasStock());
		toElement.setImage(fromElement.getImage());
		toElement.setUpdated(DateUtils.getCurrent());
	}

	private Promo findActual(List<Promo> currentPromos, Promo newPromo, PromoTitleEquals comp) {
		for (Promo p : currentPromos) {
			if (comp.equals(p, newPromo)) {
				return p;
			}
		}
		return null;
	}

	private boolean evalCurrentPromos(final List<Promo> promos, final Promo cp, final Equalator<Promo> comp) {
		ListIterator<Promo> it = promos.listIterator();
		while (it.hasNext()) {
			Promo p = it.next();
			// verificamos si ya existe la promo en la db
			if (comp.equals(cp, p)) {
				// existe, la quitamos de la lista de promos encontradas, pero verificamos si cambio la imagen para actualizar el stock
				it.remove();
				if (!cp.getImage().equals(p.getImage())) {
					copyTo(p, cp);
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

	private static interface Equalator<T> {
		
		public boolean equals(T o1, T o2);
	
	}
	
	private static class PromoTextEquals implements Equalator<Promo> {
		
		@Override
		public boolean equals(Promo o1, Promo o2) {
			return o1.getText().equalsIgnoreCase(o2.getText());
		}
		
	}
	
	private static class PromoTitleEquals implements Equalator<Promo> {
		
		@Override
		public boolean equals(Promo o1, Promo o2) {
			return o1.getTitle().equalsIgnoreCase(o2.getTitle());
		}
		
	}
	
	private static class NoPromosException extends Exception {
		
	}
	
}

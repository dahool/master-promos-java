package com.ar.sgt.masterpromos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ar.sgt.masterpromos.NotificationService;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.model.Promo;
import com.ar.sgt.masterpromos.mvc.EventForm;
import com.ar.sgt.masterpromos.utils.DateUtils;

@Controller
@RequestMapping("/ha")
public class SetupController {

	private static final String IMAGE = "https://mcservices-82aca.appspot.com/resources/info.jpg";
	
	@Autowired
	private PromoDao promoDao;
	
	@Autowired
	private NotificationService notifyService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/event", method = RequestMethod.GET)
	public String createEvent() {
		return "event";
	}
	
	@RequestMapping(value="/event", method = RequestMethod.POST)
	public String saveEvent(EventForm form) {
		Promo promo = new Promo();
		promo.setCreated(DateUtils.getCurrent());
		promo.setExpires(form.getEventExpire());
		promo.setText(form.getEventText());
		promo.setTitle(form.getEventTitle());
		promo.setUrl(form.getEventUrl());
		promo.setImage(IMAGE);
		promoDao.save(promo);
		
		//notifyService.sendNotification();
		
		return "event";
	}
	
}

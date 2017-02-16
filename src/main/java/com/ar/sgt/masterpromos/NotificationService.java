package com.ar.sgt.masterpromos;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ar.sgt.masterpromos.dao.ClientDao;
import com.ar.sgt.masterpromos.dao.PromoDao;
import com.ar.sgt.masterpromos.fcm.FcmClient;
import com.ar.sgt.masterpromos.fcm.request.FcmRequest;
import com.ar.sgt.masterpromos.fcm.response.FcmResponse;
import com.ar.sgt.masterpromos.fcm.response.FcmResponseResult;
import com.ar.sgt.masterpromos.fcm.types.ErrorCodeEnum;
import com.ar.sgt.masterpromos.fcm.types.PriorityEnum;
import com.ar.sgt.masterpromos.model.Client;
import com.ar.sgt.masterpromos.model.Promo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationService {

	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private PromoDao promoDao;
	
	@Autowired
	private FcmClient fcmClient;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void sendNotification() {

		List<Promo> promos = promoDao.listAll();
		
		ObjectMapper mapper = new ObjectMapper();

		String data = null;
		try {
			data = mapper.writeValueAsString(promos);
		} catch (JsonProcessingException e) {
			logger.error("{}", e);
		}
		
		logger.info("Send {}", data);
		
		for (Client c : clientDao.listAll()) {
			sendMessageToClient(c, data);
		}

	}

	private void sendMessageToClient(Client client, String data) {

		logger.info("Notify: {}", client.getDeviceId());
		
		try {
			FcmRequest request = new FcmRequest();
			request.setCollapseKey("new-promo-key-event");
			request.setPriority(PriorityEnum.High);
			request.setData(new HashMap<String, String>());
			request.getData().put("DATA", data);
			request.setRecipient(client.getRegId());
			
			FcmResponse response = fcmClient.send(request);
			
			logger.debug("Success: {}", response.getSuccessCount());
			logger.debug("Errors: {}", response.getFailureCount());
			
			if (response.getFailureCount() > 0) {
				for (FcmResponseResult result : response.getResults()) {
					if ((ErrorCodeEnum.NotRegistered.equals(result.getError()) || ErrorCodeEnum.InvalidRegistration.equals(result.getError()))) {
						logger.warn("Removing regId {}", client.getDeviceId());
						clientDao.delete(client);
					} else {
						logger.warn("Error: {}", result.getError());
					}
				}
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		
	}
		
}

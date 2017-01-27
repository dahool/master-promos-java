package com.ar.sgt.masterpromos;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ar.sgt.masterpromos.dao.ClientDao;
import com.ar.sgt.masterpromos.fcm.FcmClient;
import com.ar.sgt.masterpromos.fcm.request.FcmRequest;
import com.ar.sgt.masterpromos.fcm.response.FcmResponse;
import com.ar.sgt.masterpromos.fcm.response.FcmResponseResult;
import com.ar.sgt.masterpromos.fcm.types.ErrorCodeEnum;
import com.ar.sgt.masterpromos.fcm.types.PriorityEnum;
import com.ar.sgt.masterpromos.model.Client;

@Component
public class NotificationService {

	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private FcmClient fcmClient;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void sendNotification() {
		
		for (Client c : clientDao.listAll()) {
			sendMessageToClient(c);
		}

	}

	private void sendMessageToClient(Client client) {

		logger.info("Notify: {}", client.getDeviceId());
		
		try {
			FcmRequest request = new FcmRequest();
			request.setCollapseKey("new-promo-key-event");
			request.setPriority(PriorityEnum.High);
			request.setData(new HashMap<String, String>());
			request.getData().put("key", "value");
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

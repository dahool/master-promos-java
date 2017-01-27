package com.ar.sgt.masterpromos.fcm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ar.sgt.masterpromos.fcm.request.FcmRequest;
import com.ar.sgt.masterpromos.fcm.response.FcmResponse;
import com.ar.sgt.masterpromos.fcm.utils.JsonUtils;


public class FcmClient {

	private String serviceUrl = "https://fcm.googleapis.com/fcm/send";
	private String apiKey;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public FcmClient(String apiKey) {
		this.apiKey = apiKey;
	}

	public FcmClient(String serviceUrl, String apiKey) {
		this.serviceUrl = serviceUrl;
		this.apiKey = apiKey;
	}

	public FcmResponse send(FcmRequest request) throws Exception {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(this.serviceUrl);
		post.addHeader("Authorization", "key=" + this.apiKey);;
		post.addHeader("Content-Type", "application/json");
		
		String jsonString = JsonUtils.getJsonString(request);
		
		logger.debug("Send: {}", jsonString);

		post.setEntity(new StringEntity(jsonString, StandardCharsets.UTF_8));
		
		String responseBody;
		try {
			HttpResponse response = client.execute(post);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				
				if (responseEntity == null) return null;
				
				responseBody = EntityUtils.toString(responseEntity);

				logger.debug("Response: {}", responseBody);
				
				// Make Sure it is fully consumed:
				EntityUtils.consume(responseEntity);
			} else {
				throw new Exception(response.getStatusLine().toString());
			}
			
		} catch (ParseException | IOException e) {
			logger.error("{}", e);
			throw new Exception(e);
		} finally {
			client.close();
		}

        return JsonUtils.fromJsonString(responseBody, FcmResponse.class);
	}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.debug", "all");
		
		FcmRequest request = new FcmRequest();
		request.setRecipient("lalalaal");
		
		FcmClient client = new FcmClient("AAAA-ThjwuE:APA91bHPmTf8dl-k-GJm9xm-nNbsGc10sb1RBnfwEXI8bp4v_D4s4o6g27sm1mxW-AUK6aZwFGr6NhsT9PgxhIhsbu35qYBtvdaLbFOKvfJSNK2ktwbC1f_4T9rFloY_t8ZAQqY3lRPV");
		client.send(request);
		
	}
	
}

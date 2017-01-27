package com.ar.sgt.masterpromos.fcm.response;

import com.ar.sgt.masterpromos.fcm.types.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FcmResponseResult {

	@JsonProperty("message_id")
	private String messageId;
	
	@JsonProperty("registration_id")
	private String registrationId;
	
	@JsonProperty("error")
	private ErrorCodeEnum error;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public ErrorCodeEnum getError() {
		return error;
	}

	public void setError(ErrorCodeEnum error) {
		this.error = error;
	}
	
}

package com.ar.sgt.masterpromos.fcm.request;

import java.util.List;

import com.ar.sgt.masterpromos.fcm.types.OperationEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value=Include.NON_NULL)
public class FcmGroupRequest {

	@JsonProperty("operation")
	private OperationEnum operation;
	
	@JsonProperty("notification_key_name")
	private String groupName;
	
	@JsonProperty("notification_key")
	private String groupKey;
	
	@JsonProperty("registration_ids")
	private List<String> registrationIds;

	public OperationEnum getOperation() {
		return operation;
	}

	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public List<String> getRegistrationIds() {
		return registrationIds;
	}

	public void setRegistrationIds(List<String> registrationIds) {
		this.registrationIds = registrationIds;
	}
	
}

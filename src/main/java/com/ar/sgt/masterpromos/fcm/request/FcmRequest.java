package com.ar.sgt.masterpromos.fcm.request;

import java.util.List;
import java.util.Map;

import com.ar.sgt.masterpromos.fcm.types.PriorityEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(value=Include.NON_NULL)
public class FcmRequest {

	@JsonProperty("to")
	private String recipient;
	
	@JsonProperty("registration_ids")
	private List<String> recipients;

	@JsonProperty("collapse_key")
	private String collapseKey;

	@JsonProperty("condition")
	private String condition;
	
	@JsonProperty("priority")
	private PriorityEnum priority;
	
	@JsonProperty("time_to_live")
	private Integer timeToLiveInSeconds;
	
	@JsonProperty("restricted_package_name")
	private String restrictedPackageName;
	
	@JsonProperty("dry_run")
	private Boolean dryRun;
	
	@JsonProperty("data")
	private Map<String, String> data;
	
	@JsonProperty("notification")
	private FcmNotification notification;

	public String getRecipient() {
		return recipient;
	}


	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public List<String> getRecipients() {
		return recipients;
	}


	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}


	public String getCollapseKey() {
		return collapseKey;
	}


	public void setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public PriorityEnum getPriority() {
		return priority;
	}


	public void setPriority(PriorityEnum priority) {
		this.priority = priority;
	}


	public Integer getTimeToLiveInSeconds() {
		return timeToLiveInSeconds;
	}


	public void setTimeToLiveInSeconds(Integer timeToLiveInSeconds) {
		this.timeToLiveInSeconds = timeToLiveInSeconds;
	}


	public String getRestrictedPackageName() {
		return restrictedPackageName;
	}


	public void setRestrictedPackageName(String restrictedPackageName) {
		this.restrictedPackageName = restrictedPackageName;
	}


	public Boolean getDryRun() {
		return dryRun;
	}


	public void setDryRun(Boolean dryRun) {
		this.dryRun = dryRun;
	}


	public Map<String, String> getData() {
		return data;
	}


	public void setData(Map<String, String> data) {
		this.data = data;
	}


	public FcmNotification getNotification() {
		return notification;
	}


	public void setNotification(FcmNotification notification) {
		this.notification = notification;
	}
//
//
//	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
//		FcmRequest r = new FcmRequest();
//		r.setRecipient("123");
//		r.setData(new HashMap<String, String>());
//		r.getData().put("key", "value");
//		r.setPriority(PriorityEnum.High);
//		r.setNotification(new FcmNotification());
//		r.getNotification().setTitle("hola");
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(System.out, r);
//	}
}


package com.ar.sgt.masterpromos.fcm.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FcmResponse {

	@JsonProperty("multicast_id")
	private String multicastId;
	
	@JsonProperty("success")
	private int successCount;
	
	@JsonProperty("failure")
	private int failureCount;

	@JsonProperty("canonical_ids")
	private String canonicalIdCount;
	
	@JsonProperty("failed_registration_ids")
	private List<String> failedRegistrationIds;
	
	@JsonProperty("results")
	private List<FcmResponseResult> results;

	public String getMulticastId() {
		return multicastId;
	}

	public void setMulticastId(String multicastId) {
		this.multicastId = multicastId;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public String getCanonicalIdCount() {
		return canonicalIdCount;
	}

	public void setCanonicalIdCount(String canonicalIdCount) {
		this.canonicalIdCount = canonicalIdCount;
	}

	public List<String> getFailedRegistrationIds() {
		return failedRegistrationIds;
	}

	public void setFailedRegistrationIds(List<String> failedRegistrationIds) {
		this.failedRegistrationIds = failedRegistrationIds;
	}

	public List<FcmResponseResult> getResults() {
		return results;
	}

	public void setResults(List<FcmResponseResult> results) {
		this.results = results;
	}
	
}

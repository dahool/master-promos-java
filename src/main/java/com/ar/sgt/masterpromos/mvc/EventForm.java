package com.ar.sgt.masterpromos.mvc;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EventForm {

	private String eventTitle;
	
	private String eventText;
	
	private String eventUrl;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private Date eventExpire;

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventText() {
		return eventText;
	}

	public void setEventText(String eventText) {
		this.eventText = eventText;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public Date getEventExpire() {
		return eventExpire;
	}

	public void setEventExpire(Date eventExpire) {
		this.eventExpire = eventExpire;
	}
	
}

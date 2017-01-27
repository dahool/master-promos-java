package com.ar.sgt.masterpromos.fcm.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class FcmNotification {

	private String body;
	
	private String title;
	
	private String icon;
	
	private String sound;
	
	private String tag;
	
	private String color;
	
	@JsonProperty("click_action")
	private String clickAction;
	
	@JsonProperty("body_loc_key")
	private String bodyLocKey;
	
	@JsonProperty("body_loc_args")
	private String bodyLocArgs;
	
	@JsonProperty("title_loc_key")
	private String titleLocKey;
	
	@JsonProperty("title_loc_args")
	private String titleLocArgs;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	public String getBodyLocKey() {
		return bodyLocKey;
	}

	public void setBodyLocKey(String bodyLocKey) {
		this.bodyLocKey = bodyLocKey;
	}

	public String getBodyLocArgs() {
		return bodyLocArgs;
	}

	public void setBodyLocArgs(String bodyLocArgs) {
		this.bodyLocArgs = bodyLocArgs;
	}

	public String getTitleLocKey() {
		return titleLocKey;
	}

	public void setTitleLocKey(String titleLocKey) {
		this.titleLocKey = titleLocKey;
	}

	public String getTitleLocArgs() {
		return titleLocArgs;
	}

	public void setTitleLocArgs(String titleLocArgs) {
		this.titleLocArgs = titleLocArgs;
	}
	
}

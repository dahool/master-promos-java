package com.ar.sgt.masterpromos.model;

import java.io.Serializable;

public class Promo extends ModelKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url;

	private String image;

	private String text;

	private String points;

	private String percentage;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("Promo [");
		b.append("text=").append(getText()).append(";");
		b.append("url=").append(getUrl()).append(";");
		b.append("image=").append(getImage()).append(";");
		b.append("points=").append(getPoints()).append(";");
		b.append("percentage=").append(getPercentage()).append("]");
		return b.toString();
	}
	
}

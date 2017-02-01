package com.ar.sgt.masterpromos.model;

import java.io.Serializable;

import com.ar.sgt.masterpromos.orm.annotation.Entity;

@Entity
public class Promo extends ModelKey implements Serializable {

	/**
	 * 
	 */
	transient private static final long serialVersionUID = 1L;

	private String url;

	private String image;

	private String text;

	private String points;

	private String percentage;

	private String title;
	
	private String dateFrom;
	
	private String dateTo;
	
	private Boolean hasStock;
	
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getHasStock() {
		return hasStock;
	}

	public void setHasStock(Boolean hasStock) {
		this.hasStock = hasStock;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("Promo [");
		b.append("key=").append(getKey()).append(";");
		b.append("title=").append(title).append(";");
		b.append("text=").append(text).append(";");
		b.append("dateFrom=").append(dateFrom).append(";");
		b.append("dateTo=").append(dateTo).append(";");
		b.append("url=").append(url).append(";");
		b.append("image=").append(image).append(";");
		b.append("points=").append(points).append(";");
		b.append("hasStock=").append(hasStock).append(";");
		b.append("percentage=").append(percentage).append("]");
		return b.toString();
	}
	
}

package com.ar.sgt.masterpromos.model;

import java.io.Serializable;
import java.util.Date;

import com.ar.sgt.masterpromos.orm.annotation.Entity;
import com.ar.sgt.masterpromos.utils.DateUtils;
import com.ar.sgt.masterpromos.utils.Equalator;


@Entity
public class Promo extends ModelKey implements Serializable {

	/**
	 * 
	 */
	transient private static final long serialVersionUID = 1L;

	transient public static final Equalator<Promo> TextEqualator = new PromoTextEquals();
	
	transient public static final Equalator<Promo> TitleEqualator = new PromoTitleEquals();
	
	private String url;

	private String image;

	private String text;

	private String points;

	private String percentage;

	private String title;
	
	private String dateFrom;
	
	private String dateTo;
	
	private Boolean hasStock = Boolean.FALSE;
	
	private Date created;
	
	private Date updated;
	
	private Date expires;
	
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

	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public void copyFrom(Promo source) {
		setText(source.getText());
		setHasStock(source.getHasStock());
		setImage(source.getImage());
		setUpdated(DateUtils.getCurrent());
	}
	
	public boolean hasChanged(Promo source) {
		return !(getText().equalsIgnoreCase(source.getText()) && getImage().equals(source.getImage()) && getHasStock().equals(source.getHasStock()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
			Promo p = (Promo) obj;
			if (p.getKey() != null && getKey() != null) {
				return getKey().equals(p.getKey());	
			} else {
				return TextEqualator.equals(this, p);
			}
		}
		return false;
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
		b.append("created=").append(created).append(";");
		b.append("updated=").append(updated).append(";");
		b.append("percentage=").append(percentage).append("]");
		return b.toString();
	}

	private static class PromoTextEquals implements Equalator<Promo> {
		@Override
		public boolean equals(Promo o1, Promo o2) {
			return o1.getText().equalsIgnoreCase(o2.getText());
		}
	}
	
	private static class PromoTitleEquals implements Equalator<Promo> {
		@Override
		public boolean equals(Promo o1, Promo o2) {
			return o1.getTitle().equalsIgnoreCase(o2.getTitle());
		}
	}
	
}

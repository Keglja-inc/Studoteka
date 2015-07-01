package com.example.modeli;

import java.io.Serializable;

/**
 * Klasa u kojoj su definirani get i set metode za objekte tipa FakultetModel
 * 
 * @author Ivan
 *
 */
public class FakultetModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String naziv, url;
	private boolean selected;
	private String postotak;

	public FakultetModel() {
		super();
	}

	public FakultetModel(String naziv, String url, String postotak) {
		super();
		this.naziv = naziv;
		this.url = url;
		this.postotak = postotak;
	}

	public String getPostotak() {
		return postotak;
	}

	public void setPostotak(String postotak) {
		this.postotak = postotak;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNaziv() {
		return naziv;
	}

	public void SetUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return naziv;
	}

}
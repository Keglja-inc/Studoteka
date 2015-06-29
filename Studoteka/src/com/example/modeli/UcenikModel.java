package com.example.modeli;

public class UcenikModel {

	private int id;
	private String ime, prezime, mail, lozinka;

	public UcenikModel(int id, String ime, String prezime, String mail,
			String lozinka) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.lozinka = lozinka;
	}

	public UcenikModel() {

	}

	public UcenikModel(String ime, String prezime, String mail, String lozinka) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.lozinka = lozinka;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
}
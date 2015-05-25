package com.example.db;

public class Profil {
	
	
	
	int id;
	String ime;
	String prezime;
	String mail;
	String lozinka;
	public Profil(int id, String ime, String prezime, String mail,
			String lozinka) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.lozinka = lozinka;
	}
	
public Profil() {
		
	}


	public Profil(String ime, String prezime, String mail, String lozinka) {
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

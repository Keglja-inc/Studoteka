package hr.foi.air.db;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Klasa koja definira lokalnu tablicu fakulteti
 * @author Ivan
 *
 */
@Table(name = "Fakulteti")
public class DBFakultet extends Model {

	@Column(name = "url")
	public String url;
	@Column(name = "naziv")
	public String naziv;
	@Column(name = "postotak")
	public String postotak;

	public DBFakultet(String url, String naziv, String postotak) {
		super();
		this.url = url;
		this.naziv = naziv;
		this.postotak = postotak;
	}

	public DBFakultet() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPostotak() {
		return postotak;
	}

	public void setPostotak(String postotak) {
		this.postotak = postotak;
	}
	
	/**
	 * Vraæa sve fakultete sortirane prema postotku iz lokalne baze podataka 
	 * @return
	 */
	public static List<DBFakultet> dohvatiSve (){
		return new Select().from(DBFakultet.class).orderBy("postotak DESC").execute();
	}

}

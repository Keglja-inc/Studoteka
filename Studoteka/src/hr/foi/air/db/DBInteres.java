package hr.foi.air.db;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Klasa koja definira lokalnu tablicu Interesi
 * @author Ivan
 *
 */
@Table(name = "Interesi")
public class DBInteres extends Model {
	@Column(name = "idInteresa")
	public int idInteresa;
	@Column(name = "naziv")
	public String naziv;

	public DBInteres() {
		super();
	}
	
	public DBInteres (Integer idInteresa, String naziv){
		super();
		this.idInteresa = idInteresa;
		this.naziv = naziv;
	}

	public int getIdInteresa() {
		return idInteresa;
	}

	public void setIdInteresa(int idInteresa) {
		this.idInteresa = idInteresa;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	/**
	 * Vraæa sve fakultete iz lokalne baze podataka koji su sortirani po nazivu
	 * @return Vraæa sve fakultete iz lokalne baze podataka koji su sortirani po nazivu
	 */
	public static List<DBInteres> dohvatiSve (){
		return new Select().from(DBInteres.class).orderBy("naziv DESC").execute();
	}
	
	/**
	 * Dohvaæa objekt tipa InteresModel iz lokalne baze podataka na temelju ID - a 
	 * @param id
	 * @return
	 */
	public static DBInteres dohvati (Integer id){
		return new Select().from(DBInteres.class).where("idInteresa =?", id).executeSingle();
	}

}

package hr.foi.air.modeli;

import java.io.Serializable;

/**
 * Klasa u kojoj su definirani get i set metode za objekte tipa InteresiModel
 * @author Ivan
 *
 */
public class InteresModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String naziv;
	private Integer id;
	private boolean selected = false;

	public InteresModel(String naziv) {
		super();
		this.naziv = naziv;
	}

	public InteresModel(Integer id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InteresModel() {
		// TODO Auto-generated constructor stub
	}

	public String getNaziv() {
		return naziv;
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

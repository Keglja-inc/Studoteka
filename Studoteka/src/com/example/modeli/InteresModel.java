package com.example.modeli;

import java.io.Serializable;

public class InteresModel implements Serializable{
	private String name;
	private Integer id;
	private boolean selected = false;
	
	public InteresModel(String name) {
		super();
		this.name = name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return name;
	}
}

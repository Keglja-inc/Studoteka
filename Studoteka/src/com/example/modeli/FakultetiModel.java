package com.example.modeli;

import java.io.Serializable;

public class FakultetiModel implements Serializable {

	  private String name;
	  private String url;
	  private boolean selected;
	  private String postotak;

	/*
	  public FakultetiModel(String name) {
	    this.name = name;
	    selected = false;
	  }

		*/
	  public String getPostotak() {
		return postotak;
	}


	public void setPostotak(String postotak) {
		this.postotak = postotak;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getName() {
	    return name;
	  }
	  
	  public void SetUrl (String url){
		  this.url = url;
	  }
	  
	  public String getUrl(){
		  return url;
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
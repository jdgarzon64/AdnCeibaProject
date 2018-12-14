package com.ceiba.adnproject.dto;

import java.io.Serializable;

public class InputDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String licence;
	public String engine;
	public String type;
	
	public InputDTO() {
		super();
	}
	
	public InputDTO(String licence, String engine,String type) {
		super();
		this.licence = licence;
		this.engine = engine;
		this.type = type;
	}

	public String getLicence() {
		return licence;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getType() {
		return type;
	}
	
}

package com.ceiba.AdnProject.dto;

import java.io.Serializable;

public class InputDTO implements Serializable{

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

	public void setLicence(String licence) {
		this.licence = licence;
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

	public void setType(String type) {
		this.type = type;
	}
	
}

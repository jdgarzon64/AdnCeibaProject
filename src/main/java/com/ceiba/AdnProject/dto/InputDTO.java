package com.ceiba.AdnProject.dto;

import java.io.Serializable;

public class InputDTO implements Serializable{

	public String licence;
	public String engine;
	
	public InputDTO() {
		super();
	}
	
	public InputDTO(String licence, String engine) {
		super();
		this.licence = licence;
		this.engine = engine;
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
}

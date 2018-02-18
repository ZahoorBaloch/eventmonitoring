package com.rockit.monitoring.model.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogicalGroup {
	
	@JsonProperty(value = "key")
	private String name;
	
	
	@JsonProperty(value = "element_type")
	private MonObjectContainter container;


	public String getName() {
		return name;
	}


	public MonObjectContainter getContainer() {
		return container;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setContainer(MonObjectContainter container) {
		this.container = container;
	}

	
	




}
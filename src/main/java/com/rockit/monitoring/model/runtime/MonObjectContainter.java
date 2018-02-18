package com.rockit.monitoring.model.runtime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonObjectContainter {

	@JsonProperty(value = "buckets")
	private List<MonObject> monObjects;

	public List<MonObject> getMonObjects() {
		return monObjects;
	}

	public void setMonObjects(List<MonObject> monObjects) {
		this.monObjects = monObjects;
	}



}

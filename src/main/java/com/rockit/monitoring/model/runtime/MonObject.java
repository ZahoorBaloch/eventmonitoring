package com.rockit.monitoring.model.runtime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonObject {
	
	@JsonProperty(value = "key")
	private String name;
	@JsonProperty(value = "status_running")
	private StatusRunning statusRunning;
	@JsonProperty(value = "status_not_running")
	private StatusNotRunning statusNotRunning;
	public String getName() {
		return name;
	}
	public StatusRunning getStatusRunning() {
		return statusRunning;
	}
	public StatusNotRunning getStatusNotRunning() {
		return statusNotRunning;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatusRunning(StatusRunning statusRunning) {
		this.statusRunning = statusRunning;
	}
	public void setStatusNotRunning(StatusNotRunning statusNotRunning) {
		this.statusNotRunning = statusNotRunning;
	}
	
	


}

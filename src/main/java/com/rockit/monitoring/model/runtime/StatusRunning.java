package com.rockit.monitoring.model.runtime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusRunning {
	@JsonProperty(value = "doc_count")
	private String docCount;

	public String getDocCount() {
		return docCount;
	}

	public void setDocCount(String docCount) {
		this.docCount = docCount;
	}


}

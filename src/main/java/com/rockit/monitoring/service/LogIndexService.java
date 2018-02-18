package com.rockit.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class LogIndexService {
	
	@Autowired
	HTTPRequest httpRequest;
	@Autowired
	Environment env;
	
	public String getAll(Model model){
		
		return httpRequest.get(env.getProperty("elastic.url")+"/log_idx/_search");
		
	}
}

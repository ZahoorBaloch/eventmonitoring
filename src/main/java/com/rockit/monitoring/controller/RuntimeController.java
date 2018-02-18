package com.rockit.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rockit.monitoring.service.LogIndexService;
import com.rockit.monitoring.service.RuntimeService;

@Controller
public class RuntimeController {
	
//	@Autowired
//	LogIndexService logIndex;
//
//	@RequestMapping("/logindex")
//	@ResponseBody
//	public String getLogIndex(Model model){
//		return logIndex.getAll(model);
//	}

	
	@Autowired
	RuntimeService runtimeService;
	
	@RequestMapping("/")
	public String rumtimeStatus(Model model){
		runtimeService.aggregationByGroup(model);
		return "runtime";
		
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String findGroups(Model model){
		return null;
	}
}

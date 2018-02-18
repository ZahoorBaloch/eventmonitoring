package com.rockit.monitoring.service;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockit.monitoring.model.runtime.LogicalGroup;

@Service
public class RuntimeService {
	public static Logger logger = Logger.getLogger(RuntimeService.class.getName());
	String response;
	
	@Autowired
	HTTPRequest httpRequest;
	@Autowired
	Environment env;
	
	@Autowired
	ElasticService elasticService;
	

	public void aggregationByGroup(Model model) {
		String params = new String(
				"{\"from\" : 0, \"size\" : 0,"+     
				"\"aggs\" : {"+         
				"\"logical_application\": {"+            
					"\"terms\": { \"field\" : \"group\" },"+             
					"\"aggs\" : {"+
					"\"element_type\": {"+                           
						"\"terms\": { \"field\" : \"type\" },"+                             
						"\"aggs\" : {"+                                    
							"\"status_running\" : {"+                                        
								"\"filter\" : { \"term\": { \"running\": true }}},"+
							"\"status_not_running\" : {\"filter\" : { \"term\": { \"running\": false }}},"+
							"\"running\" : { \"sum\" : { \"field\" : \"running\" }}"+                                       
							"}"+
					"}}"+
				"}}}");
				
		String response = httpRequest.post(env.getProperty("elastic.url") + "/cfg_idx/_search", params);
		ObjectMapper mapper = new ObjectMapper();

		try {
//			JsonNode jsonRS = mapper.readTree(response).findValue("buckets");
//			LogicalGroup logicalGroup = mapper.treeToValue(jsonRS, LogicalGroup.class);
//			List<Group> groups = mapper.treeToValue(jsonRS, List.class);
//			model.addAttribute("jsonRStatus",  mapper.writeValueAsString(logicalGroup));
			
			String buckets = mapper.readTree(response).findValue("buckets").toString();
			List<LogicalGroup> groups = mapper.readValue(buckets, mapper.getTypeFactory().constructCollectionType(List.class, LogicalGroup.class));
			model.addAttribute("groups", groups);
			
		} catch (JsonParseException jpex) {
			logger.error(jpex.getMessage());
			
		} catch (JsonMappingException jmex) {
			logger.error(jmex.getMessage());
			
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}
	
	public void findGroup() throws UnknownHostException{
		elasticService.client().prepareSearch()
//		        .setQuery( "/* your query */" )
		        .addAggregation(
		        		AggregationBuilders.terms("logical_application").field("group")
		                .subAggregation(AggregationBuilders.terms("").field(""))
		         )
		        .execute().actionGet();
	}
}

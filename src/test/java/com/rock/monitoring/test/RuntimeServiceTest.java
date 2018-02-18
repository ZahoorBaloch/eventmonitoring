package com.rock.monitoring.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockit.monitoring.model.runtime.LogicalGroup;
import com.rockit.monitoring.model.runtime.MonObject;
import com.rockit.monitoring.service.ElasticService;
import com.rockit.monitoring.service.HTTPRequest;
import com.rockit.monitoring.service.RuntimeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { RuntimeService.class, HTTPRequest.class,ElasticService.class })
public class RuntimeServiceTest {
	
	public static Logger logger = Logger.getLogger(RuntimeServiceTest.class.getName());

	@Autowired
	RuntimeService runtimeService;
	
	@Test
	public void aggregationByGroupTest(){
		String response= "{"+
				" \"logical_application\": {"+
					" \"doc_count_error_upper_bound\": 0,"+
					" \"sum_other_doc_count\": 0,"+
					" \"buckets\": ["+
					" {"+
						" \"key\": \"yefym\","+
						" \"doc_count\": 20,"+
						" \"element_type\": {"+
							" \"doc_count_error_upper_bound\": 0,"+
							" \"sum_other_doc_count\": 0,"+
							" \"buckets\": ["+
								" {"+
								" \"key\": \"broker\","+
								" \"doc_count\": 1,"+
								" \"running\": {"+
								" \"value\": 1,"+
								" \"value_as_string\": \"true\""+
								" },"+
								" \"status_not_running\": {"+
								" \"doc_count\": 0"+
								" },"+
								" \"status_running\": {"+
								" \"doc_count\": 1"+
							" }"+
					" }"+
				" ]"+
				" }"+
				" }"+
				" ]"+
				" }"+
				" }";
		
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			String buckets = mapper.readTree(response).findValue("buckets").toString();
			List<LogicalGroup> groups = mapper.readValue(buckets, mapper.getTypeFactory().constructCollectionType(List.class, LogicalGroup.class));
			
			assertTrue("groupKey:",groups.get(0).getName().equals("yefym"));
			
			MonObject broker = groups.get(0).getContainer().getMonObjects().get(0);
			assertTrue("brokerKey:",broker.getName().equals("broker"));
			assertTrue("StatusNotRunning:",broker.getStatusNotRunning().getDocCount().equals("0"));
			assertTrue("StatusRunning:",broker.getStatusRunning().getDocCount().equals("1"));
			
			
			
		} catch (JsonParseException jpex) {
			logger.error(jpex.getMessage());
			
		} catch (JsonMappingException jmex) {
			logger.error(jmex.getMessage());
			
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

	}
	
	@Test
	public void findGroup() throws UnknownHostException{
		runtimeService.findGroup();
	}

}

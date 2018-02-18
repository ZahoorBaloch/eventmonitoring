package com.rockit.monitoring.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {

	@Autowired
	Environment env;
	
	
	private TransportClient client;

	@PostConstruct
	public void create() throws UnknownHostException {
		
		Settings settings = Settings.settingsBuilder().put("cluster.name", env.getProperty("cluster.name")).build();
		client = TransportClient.builder().settings(settings).build().addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName(env.getProperty("elastic.host")), 
						env.getProperty("elastic.port.java", Integer.class).intValue()
					));

	}
	
	@PreDestroy
	public void destroy()  {
		client.close();
	}


	public TransportClient client() {
		return client;
	}
}



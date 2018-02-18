package com.rockit.monitoring.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HTTPRequest {
	public static Logger logger = Logger.getLogger(HTTPRequest.class.getName());
	String response = null;
	
	public String get(String url) {

		InputStream in = null;
		
		try {
			in = new URL(url).openStream();
			response = IOUtils.toString(in);
			
		} catch (MalformedURLException mfex) {
			logger.error("HTTPRequest:", mfex);;
			
		} catch (IOException ioex) {
			logger.error("HTTPRequest:", ioex);;
			
		} finally {
			if(in != null){
				IOUtils.closeQuietly(in);
			}
		}

		return response;
	}
	
	public String post(String url, String jsonParams){
		HttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(jsonParams);
			
			request.setEntity(params);
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			HttpResponse httpResponse = httpClient.execute(request);
			
			response = IOUtils.toString(httpResponse.getEntity().getContent());
			logger.info(response);
		} catch (UnsupportedEncodingException uencodex) {
			logger.error(uencodex.getMessage());
			
		} catch (ClientProtocolException cplex) {
			logger.error(cplex.getMessage());
			
		} catch (IOException ioex) {
			logger.error(ioex.getMessage());
		}
		
		return response;
		
	}
}

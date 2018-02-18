package com.rock.monitoring.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import com.rockit.monitoring.service.ElasticService;

@RunWith(SpringRunner.class)
@TestComponent
@SpringBootTest(classes = { ElasticService.class })
public class ElasticServiceTest {
	
	@Autowired
	ElasticService elastic;
	
	@Test
	public void testCreate() {
		assertNotNull("at least one node has to be there", elastic.client().nodeName());
		
	}


}

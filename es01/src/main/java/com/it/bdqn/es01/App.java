package com.it.bdqn.es01;

import java.net.InetAddress;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

public class App {
    
	@Test
	public void createIndex() throws Exception{
		Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 
				9301));
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 
				9302));
		
		client.admin().indices().prepareCreate("blog2").get();
		client.close();
	}
	@Test
	public void createMappings() throws Exception{
		Settings settings = Settings.builder().put("cluster.name", "my‐elasticsearch").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 
				9301));
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 
				9302));
		
		/**
	mappings:{
    	"article": {
	        "properties": {
	            "id": {
	                "type": "long", 
	                "store": true
	            }, 
	            "title": {
	                "type": "text", 
	                "store": true, 
	                "index": true, 
	                "analyzer": "standard"
	            }, 
	            "content": {
	                "type": "text", 
	                "store": true, 
	                "index": true, 
	                "analyzer": "standard"
	            }
	        }
	    }
	}
*/
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject()
				.startObject("article")
					.startObject("properties")
						.startObject("id").field("type", "long").field("store", "yes")
							.endObject()
						.startObject("title").field("type", "string").field("store", "yes").field("analyzer","standard")
							.endObject()
						.startObject("content").field("type", "string").field("store", "yes").field("analyzer","standard")
							.endObject()
					.endObject()
				.endObject()
			.endObject();
		
		PutMappingRequest pr = Requests.putMappingRequest("blog2").type("article").source(builder);
		
		client.admin().indices().putMapping(pr).get();
		client.close();
	}
}

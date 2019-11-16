package com.suchunhao.client;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestFulClient {

	static final String REST_URI = "http://localhost:9080";
	static final String ADDRESS_PATH = "/server1";
	// static final String SUB_PATH = "calc/sub";

	public static void main(String[] args) {
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);

			// server1
			System.out.println("please input 1 for get a address: ");
			System.in.read();
			WebResource addService1 = service.path(ADDRESS_PATH).path("/school");
			String addressInfo = getOutputAsText(addService1);
			System.out.println("server1 result: " + addressInfo);

			// server2
			System.out.println("please input 2 for get " + addressInfo + " location: ");
			System.in.read();
			WebResource addService2 = service.path(ADDRESS_PATH).path("/getLocation").path("/" + addressInfo);
			String location = getOutputAsText(addService2);
			System.out.println("server2 result: " + location);

			// server 3
			System.out.println("please input 2 for get " + addressInfo + " location: ");
			System.in.read();
			JSONObject jsonObject = new JSONObject(location);
			String lat = jsonObject.getString("lat");
			String lng = jsonObject.getString("lng");
			WebResource addService3 = service.path(ADDRESS_PATH).path("/getTimeZone").path("/" + lat).path("/" + lng);
			String timeZone = getOutputAsText(addService3);
			System.out.println("server3 result: " + timeZone);

			System.out.println("--------------end-------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getOutputAsText(WebResource service) {
		return service.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}

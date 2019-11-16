package com.suchunhao.restful;

import java.io.IOException;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class RestServerApplication {

	static final String BASE_URI = "http://localhost:9080/";

	public static void main(String[] args) {
		try {
			HttpServer server = HttpServerFactory.create(BASE_URI);
			server.start();
			System.out.println("start success");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

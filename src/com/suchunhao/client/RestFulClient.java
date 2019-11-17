package com.suchunhao.client;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestFulClient {

	static final String REST_URI = "http://localhost:9080/server1";
	static final String ADDRESS_PATH = "/getAddress";
	static final String LOCATION_PATH = "/getLocation";
	static final String TIMEZONE_PATH = "/getTimeZone";

	public static void main(String[] args) {
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);

			// server1
			System.out.println("please input 1 for get a address: ");
			String inputDialog = JOptionPane.showInputDialog(null, "请输入地址,如果不输入我将会去server1查询默认的地址", "获取地址", JOptionPane.YES_NO_OPTION);
			if (inputDialog == null || "".equals(inputDialog)) {
				WebResource addService1 = service.path(ADDRESS_PATH).path("/school");
				String addressInfo = getOutputAsText(addService1);
				System.out.println("server1 result: " + addressInfo);
				inputDialog = addressInfo;
			}

			// server2
			System.out.println("please input 2 for get " + inputDialog + " location: ");
			int res1 = JOptionPane.showConfirmDialog(null, "点击确定获取" + inputDialog + "经纬度", "获取经纬度", JOptionPane.YES_NO_OPTION);
			if (res1 == JOptionPane.NO_OPTION) {
				return;
			}
			WebResource addService2 = service.path(LOCATION_PATH).path("/" + inputDialog);
			String location = getOutputAsText(addService2);
			System.out.println("server2 result: " + location);

			JSONObject jsonObject = new JSONObject(location);
			String lat = jsonObject.getString("lat");
			String lng = jsonObject.getString("lng");
			// server 3
			System.out.println("please input 3 for get " + inputDialog + " location: ");
			int res2 = JOptionPane.showConfirmDialog(null, "点击确定获取" + lat + "," + lng + "的时区", "获取时区", JOptionPane.YES_NO_OPTION);
			if (res2 == JOptionPane.NO_OPTION) {
				return;
			}

			WebResource addService3 = service.path(TIMEZONE_PATH).path("/" + lat).path("/" + lng);
			String timeZone = getOutputAsText(addService3);
			JSONObject jsonObject1 = new JSONObject(timeZone);
			String timeZoneId = jsonObject1.getString("timeZoneId");
			String timeZoneName = jsonObject1.getString("timeZoneName");
			String server3Result = "timeZoneId:" + timeZoneId + ",timeZoneName:" + timeZoneName;
			JOptionPane.showMessageDialog(null, server3Result);
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

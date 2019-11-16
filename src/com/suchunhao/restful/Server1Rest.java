package com.suchunhao.restful;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

@Path("/server1")
public class Server1Rest {

	private static Map<String, String> addressMap = new HashMap<>();
	private static RestClient restClient;

	// init default
	static {
		addressMap.put("school", "University of Leeds");
		restClient = new RestClient();
	}

	@GET
	@Path("/getAddress/{label}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAddress(@PathParam("label") String label) {
		String addressResult = addressMap.get(label);
		if (addressResult == null || "".equals(addressResult)) {
			return "not find " + label;
		}
		String remind = "you can request server2 http://localhost:9080/server1/getLocation/" + addressResult + " , get Location ";
		return remind;
	}

	@GET
	@Path("/addAddress/{label}/{addressInfo}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addAddress(@PathParam("label") String label, @PathParam("addressInfo") String addressInfo) {
		addressMap.put(label, addressInfo);
		return "success";
	}

	@GET
	@Path("/getLocation/{address}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLocation(@PathParam("address") String address) {
		if (address == null || "".equals(address)) {
			return "not empty " + address;
		}
		try {
			JSONObject jsonObject = restClient.addressLocationApi(address);
			String lat = jsonObject.getString("lat");
			String lng = jsonObject.getString("lng");
			String remind = "you can request GET http://localhost:9080/server1/getTimeZone/" + lat + "/" + lng + ",  to get TimeZone";
			return remind;
		} catch (Exception e) {
			return " sorry error i will fix it ";
		}
	}

	@GET
	@Path("/getTimeZone/{lat}/{lng}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTimeZone(@PathParam("lat") String lat, @PathParam("lng") String lng) {
		if (lat == null || "".equals(lat)) {
			return "not empty " + lat;
		}
		if (lat == null || "".equals(lng)) {
			return "not empty " + lng;
		}
		try {
			JSONObject jsonObject = restClient.timeZoneApi(lat, lng);
			return jsonObject.toString();
		} catch (Exception e) {
			return " sorry error i will fix it ";
		}
	}
}

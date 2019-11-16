package com.suchunhao.restful;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

		String remind = "you can request   http://localhost:9080/server1/getLocation/" + addressResult + " , get Location ";
		return remind;
	}

	@POST
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
			return jsonObject.toString();
		} catch (Exception e) {
			return " sorry error i well fix it ";
		}
	}

	@GET
	@Path("/getTimeZone/{location}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTimeZone(@PathParam("location") String location) {
		if (location == null || "".equals(location)) {
			return "not empty " + location;
		}
		try {
			JSONObject jsonObject1 = new JSONObject(location);
			JSONObject jsonObject = restClient.timeZoneApi(jsonObject1.getString("lat"), jsonObject1.getString("lng"));
			return jsonObject.toString();
		} catch (Exception e) {
			return " sorry error i well fix it ";
		}
	}

}

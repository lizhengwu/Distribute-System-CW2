package com.suchunhao.restful;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class RestClient {

	private final static String GEO_CODING_URI = "https://maps.googleapis.com/maps/api/geocode/json?";
	private final static String TIME_ZONE_URI = "https://maps.googleapis.com/maps/api/timezone/json?";

	// 这些是我们在谷歌服务器上申请的每个API_KEY
	private final static String GEO_CODING_API_KEY = "AIzaSyBvZbHPaF79FEVD4tTJDkDQRiwmoBat0lc";
	private final static String TIME_ZONE_API_KEY = "AIzaSyDT7k-4tJ_ctBSydn-sYyvx4ljiSrh0xcw";

	/**
	 * 获取地址的经纬度
	 *
	 * @param address your Address
	 * @return
	 */
	public JSONObject addressLocationApi(String address) throws Exception {

		String formatAddress = address.replaceAll(" ", "+");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(GEO_CODING_URI).append("address=").append(formatAddress).append("&key=").append(GEO_CODING_API_KEY);

		String result = this.GetMessageForHttp(stringBuilder.toString());
		JSONObject jsonObject = new JSONObject(result);
		JSONArray jsonArray = (JSONArray) jsonObject.get("results");
		JSONObject results = (JSONObject) jsonArray.get(0);
		JSONObject geometry = (JSONObject) results.get("geometry");
		JSONObject location = (JSONObject) geometry.get("location");

		return location;
	}

	/**
	 * 根据经纬度获取时区
	 *
	 * @param lat
	 * @param lng
	 * @return
	 */
	public JSONObject timeZoneApi(String lat, String lng) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(TIME_ZONE_URI).append("location=").append(lat).append(",").append(lng).append("&timestamp=1458000000").append("&key=").append(TIME_ZONE_API_KEY);
		String result = this.GetMessageForHttp(stringBuilder.toString());
		return new JSONObject(result);
	}

	public String GetMessageForHttp(String urlString) throws Exception {
		URL url;
		HttpURLConnection connection = null;
		InputStream is = null;

		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			is = connection.getInputStream();
			BufferedReader theReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String reply;
			while ((reply = theReader.readLine()) != null) {
				return reply;
			}
			return reply;
		} catch (Exception e) {
			throw new Exception();
		} finally {
			connection.disconnect();
		}

	}

	public static void main(String[] args) {
		try {
			RestClient restClient = new RestClient();
			restClient.GetMessageForHttp("https://maps.googleapis.com/maps/api/geocode/json?address=University+of+Leeds&key=AIzaSyBvZbHPaF79FEVD4tTJDkDQRiwmoBat0lc");
		} catch (Exception e) {

		}

	}
}

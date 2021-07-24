package com.mlog.recom.common.util;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class HttpCommUtilTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String getHttpResult = httpGetTest();
		//System.out.println("getHttpResult: " + getHttpResult);
		
		//String getHttpsResult = httpsGetTest();
		//System.out.println("getHttpsResult: " + getHttpsResult);
		
		String postHttpResult = httpPostTest();
		System.out.println("postHttpResult: " + postHttpResult);

	}
	
	private static String httpPostTest() {
		// TODO Auto-generated method stub
		
		//HTTP 통신 Header 정보 
    	Map<String, String> headerMap = new HashMap<>();
    	headerMap.put("Content-Type", "application/json");
    	headerMap.put("Accept", "application/json");
		
    	//HTTP 통신 Parameter 정보 
		Map<String, String> param = new HashMap<>();
		param.put("profile_id", "");
		param.put("stb_mac", "");
		param.put("service_type", "K");
		param.put("recom_id", "");
		
    	//HTTP 통신 Request Body 정보 
		JSONObject jsonRequestBody = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("gender", "");
		jsonObject.put("age", "");
		jsonObject.put("english_lv", "");
		jsonObject.put("keyword_list", "");
		jsonObject.put("keyword_score", "");
		jsonObject.put("album_list", "");
		jsonObject.put("album_score", "");
		jsonObject.put("diagnose_list", "");
		jsonObject.put("diagnose_score", "");

		jsonRequestBody.put("param", jsonObject);
		String body = jsonRequestBody.toJSONString();

		//Http 통신
		String result ="";
		
		try {
			HttpCommUtil httpCommUtil = new HttpCommUtil();

			result = httpCommUtil.callHttpClient("http://localhost:8080/recom/Test/infer/list", param, body, "UTF-8", headerMap, "POST", Integer.parseInt("30000"), false);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static String httpsGetTest() {
		// TODO Auto-generated method stub
		String result = "";
		
		Map<String, String> headerMap = new HashMap<>();
    	headerMap.put("Content-Type", "application/json");
    	headerMap.put("Accept", "application/json");
		
		Map<String, String> param = new HashMap<>();
		param.put("sa_id", "%2b%2b%2bKjkQTYJD%2fl8Mf8I9Tr8K2n824BtSCW1TI4R4BML7zLHZ7DyjJXYsh6%2f18ClLenDzdGC7kcUydiB536n27Xg%3d%3d");
		param.put("stb_mac", "vv10.9999.9001");
		param.put("service_type", "N");
		param.put("container_id", "prefer_iptv_007");
		param.put("start_index", "1");
		param.put("request_cnt", "10");
		param.put("version", "2021031917_1_91");
		
		try {
			HttpCommUtil httpCommUtil = new HttpCommUtil();

			result = httpCommUtil.callHttpClient("https://arestb.uplus.co.kr/recom/tv/contents/list", param, null, "UTF-8", headerMap, "GET", Integer.parseInt("30000"), true);
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String httpGetTest() {
		String result = "";
		
		Map<String, String> headerMap = new HashMap<>();
    	headerMap.put("Content-Type", "application/json");
    	headerMap.put("Accept", "application/json");
		
		Map<String, String> param = new HashMap<>();
		param.put("sa_id", "M01099999001");
		param.put("stb_mac", "vv10.9999.9001");
		param.put("cat_id", "root");
		param.put("start_num", "1");
		param.put("req_count", "10");
		param.put("version", "20210104093940");
		param.put("search_typ", "A");
		
		try {
			HttpCommUtil httpCommUtil = new HttpCommUtil();

			result = httpCommUtil.callHttpClient("http://mimstb.uplus.co.kr/smartux/getKeywordList", param, null, "UTF-8", headerMap, "GET", Integer.parseInt("30000"), false);
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}

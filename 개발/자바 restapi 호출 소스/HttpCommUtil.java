package com.mlog.recom.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class HttpCommUtil {
	
	public String callHttpClient(String url,  Map<String, String> param, String body, String encoding, Map<String, String> header, String Method, int timeout, boolean isSsl) throws Exception{
		String result = "";
		HttpRequestBase method_type = null;
		
		HttpClient httpClient = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];

		try {
			if (!CollectionUtils.isEmpty(param)) {
				url = url+ "?";
				for (Map.Entry<String, String> entry : param.entrySet()) {
					url = url+ entry.getKey()+"="+entry.getValue()+"&";
				}
				url = url.substring(0,url.length()-1);
			}
			
			if("POST".equals(Method.toUpperCase())){
				method_type = new HttpPost(url);
				((HttpEntityEnclosingRequestBase) method_type).setEntity(new StringEntity(body, encoding));

			}else if("PUT".equals(Method.toUpperCase())){
				method_type = new HttpPut(url);
			}else if("DELETE".equals(Method.toUpperCase())){
				method_type = new HttpDelete(url);
			}else{
				method_type = new HttpGet(url);
			}

			if(!CollectionUtils.isEmpty(header)){
				for( String key : header.keySet() ){
					method_type.setHeader(key, header.get(key));
				}
			}

			httpClient = new DefaultHttpClient();
			
			if(isSsl){
				TrustManager easyTrustManager = new X509TrustManager() {

					public X509Certificate[] getAcceptedIssuers() {
						// no-op
						return null;
					}

					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}

					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}
				};
				
				SSLContext sslcontext = SSLContext.getInstance("TLS");
				sslcontext
						.init(null, new TrustManager[] { easyTrustManager }, null);

				SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				
				Scheme sch = new Scheme("https", 443, socketFactory);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			}
			
			HttpParams params = httpClient.getParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);
            
			response = httpClient.execute(method_type);
			entity = response.getEntity();
			is = entity.getContent();
			sb = new StringBuffer();
			
			for(int n; (n =is.read(b)) != -1;){
				sb.append(new String(b, 0, n));
			}
			result = sb.toString();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		} finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
	/**
	 * JSONObject에서 String을 추출
	 * @param object JSONObject
	 * @param key Key
	 * @return
	 */
	public String getJsonString(JSONObject object, String key) {
        String result = "";
        if (object.containsKey(key)) {
        	result = object.get(key) != null?object.get(key).toString():"";
        }
        return result;
    }
}

package com.ixiudou.util;

import okhttp3.*;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/10/15 13:49
 * @company：CTTIC
 */
public class OKHttpClientUtil {
	private static int connTimeOut = 5;
	private static int readTimeOut = 20;
	private static int writeTimeOut = 10;
	public static OkHttpClient client = null;

	static {
		client = new OkHttpClient.Builder()
			.connectTimeout(5L, TimeUnit.SECONDS)
			.readTimeout(20L, TimeUnit.SECONDS)
			.writeTimeout(10L, TimeUnit.SECONDS)
			.retryOnConnectionFailure(true)
			.build();
	}

	public OKHttpClientUtil() {
	}


	public static String doGet(String url, Map<String, String> headers, Map<String, String> param) throws Exception {
		StringBuffer urlS = new StringBuffer(url);
		//StringBuffer url = new StringBuffer(host + (path == null?"":path));
		if(param != null) {
			urlS.append("?");
			Iterator iterator = param.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> e = (Map.Entry)iterator.next();
				urlS.append((String)e.getKey()).append("=").append((String)e.getValue() + "&");
			}
			urlS = new StringBuffer(urlS.substring(0,urlS.length()-1));
		}
		Request.Builder requestBuilder = new Request.Builder();
		if(headers != null && headers.size() > 0) {
			Iterator iterator = headers.keySet().iterator();
			while(iterator.hasNext()) {
				String key = (String)iterator.next();
				requestBuilder.addHeader(key, (String)headers.get(key));
			}
		}
		Request request = (requestBuilder).url(urlS.toString()).build();
		Response response = client.newCall(request).execute();
		String responseStr = response.body() == null?"":response.body().string();
		return responseStr;
	}

	public static String doPost(String url, Map<String, String> headers, Map<String, String> querys) throws Exception {
		FormBody.Builder formbody = new FormBody.Builder();
		if(null != querys){
			Iterator iterator = querys.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> elem = (Map.Entry)iterator.next();
				formbody.add((String)elem.getKey(), (String)elem.getValue());
			}
		}

		RequestBody body = formbody.build();
		Request.Builder requestBuilder = (new Request.Builder()).url(url);
		if(headers != null && headers.size() > 0) {
			Iterator iteratorHeader = headers.keySet().iterator();
			while(iteratorHeader.hasNext()) {
				String key = (String)iteratorHeader.next();
				requestBuilder.addHeader(key, (String)headers.get(key));
			}
		}

		Request requet = requestBuilder.post(body).build();
		Response response = client.newCall(requet).execute();
		String responseStr = response.body() == null?"":response.body().string();
		return responseStr;
	}

	public static String doPost(String url, Map<String, String> headers, String sendMessage) throws Exception {

		RequestBody body = FormBody.create(MediaType.parse("application/json"), sendMessage);
		Request.Builder requestBuilder = (new Request.Builder()).url(url);
		if(headers != null && headers.size() > 0) {
			Iterator iteratorHeader = headers.keySet().iterator();
			while(iteratorHeader.hasNext()) {
				String key = (String)iteratorHeader.next();
				requestBuilder.addHeader(key, ((String)headers.get(key)).trim());
			}
		}

		Request requet = requestBuilder.post(body).addHeader("Content-Type","application/json").build();
		Response response = client.newCall(requet).execute();
		String responseStr = response.body() == null?"":response.body().string();
		return responseStr;
	}

	public static String doPut(String host, String path, Map<String, String> headers, Map<String, String> querys) throws Exception {
		FormBody.Builder builder = new FormBody.Builder();
		Iterator iterator = querys.entrySet().iterator();

		while(iterator.hasNext()) {
			Map.Entry<String, String> elem = (Map.Entry)iterator.next();
			builder.add((String)elem.getKey(), (String)elem.getValue());
		}

		RequestBody body = builder.build();
		Request.Builder requestBuilder = (new Request.Builder()).url(host + path);
		if(headers != null && headers.size() > 0) {
			Iterator iteratorHeader = headers.keySet().iterator();
			while(iteratorHeader.hasNext()) {
				String key = (String)iteratorHeader.next();
				requestBuilder.addHeader(key, (String)headers.get(key));
			}
		}

		Request requet = requestBuilder.put(body).build();
		Response response = client.newCall(requet).execute();
		String responseStr = response.body() == null?"":response.body().string();
		return responseStr;
	}
}

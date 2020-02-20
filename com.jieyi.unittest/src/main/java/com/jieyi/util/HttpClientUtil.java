package com.jieyi.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static String sendMsgAndRecv(String url, List<NameValuePair> list)
			throws Exception {
		String sRet = "-1";
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost(url);
			// HttpPost post = new
			// HttpPost("http://127.0.0.1:8080/jytestreport/restful/biz.do"); //
			// 这里用上本机的某个工程做测试
			// 创建参数列表
			// List<NameValuePair> list = new ArrayList<NameValuePair>();
			// list.add(new BasicNameValuePair("j_username", "admin"));
			// list.add(new BasicNameValuePair("j_password", "admin"));
			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,
					"UTF-8");
			post.setEntity(uefEntity);
			System.out.println("POST 请求...." + post.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out
							.println("-------------------------------------------------------");
					sRet = EntityUtils.toString(entity);
					System.out.println(sRet);
					System.out
							.println("-------------------------------------------------------");

				}
			} finally {
				httpResponse.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sRet;
	}

	private static CloseableHttpClient getHttpClient() {
		// return HttpClients.createDefault();

		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.setStaleConnectionCheckEnabled(true).build();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();
		return httpclient;
	}

	private static void closeHttpClient(CloseableHttpClient client)
			throws IOException {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String httpPostJson(String url, String str1)
			throws IOException {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		String str = null;
		try {
			// 解决中文乱码问题
			StringEntity entity = new StringEntity(str1, "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {

				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("result.getStatusLine().getStatusCode():"+result.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return str;
	}

	public static String sendMsgAndRecvByHttps(String url,
			List<NameValuePair> list) throws Exception {
		CloseableHttpClient httpClient = null;
		HttpPost post = null;
		String result = null;
		String sRet = "-1";
		try {
			httpClient = new SSLClient();
			post = new HttpPost(url);

			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,
					"UTF-8");
			post.setEntity(uefEntity);
			System.out.println("HTTPS POST 请求...." + post.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					System.out
							.println("-------------------------------------------------------");
					sRet = EntityUtils.toString(entity);
					System.out.println(sRet);
					System.out
							.println("-------------------------------------------------------");

				}
			} finally {
				httpResponse.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sRet;
	}
	
	public static String postJsonByHttps(String url,
			String str1) throws Exception {

		
		CloseableHttpClient httpClient = null;
		HttpPost method = null;
		String result = null;
		String sRet = "-1";
		try {
			httpClient = new SSLClient();
			method = new HttpPost(url);

			// url格式编码
//			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,
//					"UTF-8");
//			method.setEntity(uefEntity);
			StringEntity entity = new StringEntity(str1, "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			method.setEntity(entity);
			System.out.println("HTTPS POST 请求...." + method.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(method);
			try {
				HttpEntity entity1 = httpResponse.getEntity();
				if (null != entity) {
					System.out
							.println("-------------------------------------------------------");
					sRet = EntityUtils.toString(entity1);
					System.out.println(sRet);
					System.out
							.println("-------------------------------------------------------");

				}
			} finally {
				httpResponse.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sRet;
	}

}

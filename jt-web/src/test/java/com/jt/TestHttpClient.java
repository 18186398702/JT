package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	/**
	 * 模拟发起get请求
	 * 1.创建httpclient对象
	 * 2.定义url路径
	 * 3.定义请求方式 get、post
	 * 4.发起request请求，获取response响应
	 * 5.判断状态码   200为成功   400请求参数异常   406返回结果与页面要求不匹配
	 *              404找不到请求路径   500服务器异常
	 * 6.获取响应结果
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void get() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "http://m.news.cctv.com/2019/04/09/ARTIsThYaww3YyEsiirdc0Jt190409.shtml?spm=C96370.PsikHJQ1ICOX.EXqHCvKOWDId.1";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(get);
		if (response.getStatusLine().getStatusCode()==200) {
			System.out.println("跨系统访问成功");
			String result = EntityUtils.toString(response.getEntity(),"utf8");
			System.out.println(result);
		}
	}
}

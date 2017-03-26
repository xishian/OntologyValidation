package edu.swu.ontologyvalidation.util;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Tuister on 2017/3/4.
 */

public class NetworkClient {
	private static OkHttpClient client = new OkHttpClient();
	private static final String SERVER_IP = "52.192.206.211";
	private static final String URL_LOGIN = "";

	public static int login(String username,String password) throws IOException {

		RequestBody body = RequestBody.create(MediaType.parse("application"),"username="+username+"&password="+password);
		Request request = new Request.Builder().url(URL_LOGIN).post(body).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		if(response.code()==200)
		{
			int result = Integer.parseInt(response.body().string());
			//result 为1时登录成功
			return result;
		}
		//返回0说明网络请求失败
		return 0;
	}

}

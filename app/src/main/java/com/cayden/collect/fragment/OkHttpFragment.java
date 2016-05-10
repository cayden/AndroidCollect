package com.cayden.collect.fragment;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cayden.collect.R;
import com.cayden.collect.fragment.base.BaseFragment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 使用
 */
public class OkHttpFragment extends BaseFragment implements View.OnClickListener{
	private static final String TAG=OkHttpFragment.class.getSimpleName();

	private static OkHttpClient client=new OkHttpClient();
	private Button syncBtn,asyncBtn;
	private TextView tvtext;
	private String result;
	private static final String SYNC_URL="http://www.weather.com.cn/data/sk/101010100.html";

	private static final String ASYNC_URL="http://www.weather.com.cn/data/sk/101010300.html";

	/**
	 * 在这里直接设置连接超时，静态方法内，在构造方法被调用前就已经初始话了
	 */
	static {
		client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
		client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
		client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
	}

	private Request request;
	@Override
	protected int setLayoutResourceID() {
		return R.layout.fragment_okhttp;
	}

	@Override
	protected void initView() {
		syncBtn=customFindViewById(R.id.syncBtn);
		asyncBtn=customFindViewById(R.id.asyncBtn);
		tvtext=customFindViewById(R.id.result);

		syncBtn.setOnClickListener(this);
		asyncBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.syncBtn:
				initSyncData();
				break;
			case R.id.asyncBtn:
				initAsyncGet();
				break;
		}
	}

	private void initSyncData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					request = new Request.Builder().url(SYNC_URL).build();
					Response response = client.newCall(request).execute();
					result = response.body().string();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tvtext.setText(result);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void initAsyncGet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				request = new Request.Builder().url(ASYNC_URL).build();
				client.newCall(request).enqueue(new Callback() {

					/**
					 * A call is a request that has been prepared for execution. A call can be canceled. As this object
					 * represents a single request/response pair (stream), it cannot be executed twice.
					 *
					 *
					 * @param call   是一个接口，  是一个准备好的可以执行的request
					 *               可以取消，对位一个请求对象，只能单个请求
					 * @param e
					 */
					@Override
					public void onFailure(Call call, IOException e) {
						Log.d("MainActivity", "请求失败");
					}

					/**
					 *
					 * @param call
					 * @param response   是一个响应请求
					 * @throws IOException
					 */
					@Override
					public void onResponse(Call call, Response response) throws IOException {
						/**
						 * 通过拿到response这个响应请求，然后通过body().string(),拿到请求到的数据
						 * 这里最好用string()  而不要用toString（）
						 * toString（）每个类都有的，是把对象转换为字符串
						 * string（）是把流转为字符串
						 */
						result = response.body().string();
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								tvtext.setText(result);
							}
						});
					}
				});
			}
		}).start();
	}
}

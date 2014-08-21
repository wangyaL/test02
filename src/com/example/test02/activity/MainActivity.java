package com.example.test02.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test02.R;
import com.example.test02.common.MyCount;
import com.example.test02.core.UserLoginResult;
import com.example.test02.core.bean.User;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	private String username,password;
	private EditText EditUsername,EditPassword;
	private Button login_btn;
	/**
	 * 用户登录地址
	 */
	private String LOGIN_URL = "/user/login.action";
	
	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditUsername = (EditText) findViewById(R.id.editText_username);
		EditPassword = (EditText) findViewById(R.id.editText_password);
		login_btn = (Button) findViewById(R.id.btn_login);
		
		login_btn.setOnClickListener(loginListenner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener loginListenner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			username = EditUsername.getText().toString();
			password = EditPassword.getText().toString();
			if (username.equals("") || password.equals("")) {
				Toast.makeText(MainActivity.this, "请输入用户名和密码！", Toast.LENGTH_LONG).show();
			}else {
//				Toast.makeText(MainActivity.this, "点击登录", Toast.LENGTH_LONG).show();
				userLogin(MainActivity.this, username, password);
			}
//			getGoods(MainActivity.this, 7);
		}
	};
	@SuppressWarnings("unused")
	private void getGoods(Context context, int areaId){
		MyCount myCount = (MyCount) getApplication();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(myCount.getURL()+"/goods/getAllGoods.action");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("areaId", areaId);
			HttpEntity entity = new StringEntity(getJsonByMap(map, null));
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				String msg = EntityUtils.toString(response.getEntity());
//				System.out.println(msg);
				
				JSONObject json = new JSONObject(msg);
//				JSONObject flag = json.getJSONObject("flag");
//				JSONObject message = json.getJSONObject("message");
//				JSONObject isSuccess = json.getJSONObject("isSuccess");
//				JSONObject info = json.getJSONObject("info");
//				Log.d("消息", flag.toString());
//				Log.d("消息", message.toString());
//				Log.d("消息", isSuccess.toString());
//				Log.d("消息", info.toString());
				
				JSONArray arrayJson = json.getJSONArray("detail");
				for(int i=0; i<arrayJson.length(); i++){
					JSONObject tempJson = arrayJson.optJSONObject(i);
					Log.d("数组"+i,tempJson.getString("goodsId"));
					Log.d("数组"+i,tempJson.getString("goodsName"));
				}
				
//				JSONArray jsonArray = new JSONArray(msg);
//				int size = jsonArray.length();
//				for (int i = 0; i < size; i++) {
//					System.out.println("msg["+i+"]=====>>");
//				}
				
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void userLogin(Context context, String username, String password){
		MyCount myCount = (MyCount) getApplication();
		System.out.println("请求的路径是=====>>"+myCount.getURL());
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(myCount.getURL()+LOGIN_URL);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", username));
			parameters.add(new BasicNameValuePair("password", password));
//			post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("password", password);
			HttpEntity entity = new StringEntity(getJsonByMap(map, null));
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				String msg = EntityUtils.toString(response.getEntity());
				System.out.println(msg);
//				Looper.prepare();
				UserLoginResult loginResult = new UserLoginResult();
				Gson gson = new Gson();
				loginResult = gson.fromJson(msg.toString(), UserLoginResult.class);
				System.out.println("===flag==>>"+loginResult.getFlag());
				System.out.println("===message==>>"+loginResult.getMessage());
				System.out.println("===isSuccess==>>"+loginResult.getIsSuccess());
				System.out.println("===info==>>"+loginResult.getInfo());
				System.out.println("===userVo==>>"+loginResult.getUserVO());
				User user = loginResult.getUserVO();
	//			UserInfo user2 = gson.fromJson(msg.get, UserInfo.class);
				if(user != null){
					System.out.println("===userid==>>"+user.getId());
					System.out.println("===username==>>"+user.getName());
				}
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//				Looper.loop();
			}else {
				Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	/**
	 * post方式时，将传入参数转换为json格式
	 * 
	 * @param params
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("rawtypes")
	private static String getJsonByMap(HashMap<String, Object> params, List<HashMap<String, Object>> childParams)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			String key = (String) pairs.getKey();
			Object value = (Object) pairs.getValue();
			jsonObject.put(key, value);
		}
		if (childParams != null) {
			for (int i = 0; i < childParams.size(); i++) {
				Iterator childIter = childParams.get(i).entrySet().iterator();
				JSONObject childJsonObject = new JSONObject();
				while (childIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) childIter.next();
					String key = (String) pairs.getKey();
					Object value = (Object) pairs.getValue();
					childJsonObject.put(key, value);
				}
				jsonArray.put(i, childJsonObject);
			}
			jsonObject.put("detail", jsonArray);
		}
		JSONObject json = new JSONObject();
		json.put("jsonString", jsonObject);
		return json.toString();
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		final MyCount myCount = (MyCount) getApplication();
//		String URL = myCount.getURL();
//		if(keyCode == KeyEvent.KEYCODE_MENU){
//			Context context = MainActivity.this;
//			final View dialogView = getLayoutInflater().inflate(R.layout.urldialog, null);
//			final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_url);
//			editText.setText(URL);
//			
//			new AlertDialog.Builder(context)
//				.setTitle("请输入")
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.setView(dialogView)
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						String url = editText.getText().toString();
//						myCount.setURL(url);
//						Log.i("", url);
//					}
//				})
//				.setNegativeButton("取消", null)
//				.show();
//			return true;
//		}else if(keyCode == KeyEvent.KEYCODE_BACK){
//			System.exit(0);
//		}
//		return false;
//	}
	/**
	 * 菜单事件
	 */
	public boolean onOptionsItemSelected(MenuItem item){
		final MyCount myCount = (MyCount) getApplication();
		String URL = myCount.getURL();
		Context context = MainActivity.this;
		switch (item.getItemId()) {
			case R.id.settings_edit_url:
				final View dialogView = getLayoutInflater().inflate(R.layout.urldialog, null);
				final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_url);
				editText.setText(URL);
				
				new AlertDialog.Builder(context)
					.setTitle("请输入访问地址")
					.setIcon(android.R.drawable.btn_star)
					.setView(dialogView)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String url = editText.getText().toString();
							myCount.setURL(url);
							Log.i("", url);
						}
					})
					.setNegativeButton("取消", null)
					.show();
				break;
			case R.id.settings_radio_url:
				final String[] strs = new String[]{"测试服务111",
						"本机服务136",
						"服务器"};
				final String[] values = new String[]{
						"http://192.168.1.111:58085/icsp-phone",
						"http://192.168.1.136:8080/icsp-phone",
						"http://218.244.146.86:58083"
					};
				int index = 0;
				for(int i=0; i<strs.length; i++){
					if(values[i] == URL){
						index = i;
					}
				}
				new AlertDialog.Builder(this)
					.setTitle("单选框")
					.setIcon(android.R.drawable.btn_star_big_on)
					.setSingleChoiceItems(strs, index, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.i(TAG, "===>>"+which);
							Log.i(TAG, "===>>"+strs[which]);
							Log.i(TAG, "===>>"+values[which]);
							myCount.setURL(values[which]);
							dialog.dismiss();
						}
					})
					.setNegativeButton("取消", null)
					.show();
				Log.i(TAG, "选择访问地址");
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
}

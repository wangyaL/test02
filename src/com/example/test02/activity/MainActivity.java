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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test02.R;
import com.example.test02.common.MyCount;
import com.example.test02.core.UserLoginResult;
import com.example.test02.core.bean.User;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	private MyCount myCount;
	private String username,password;
	private EditText EditUsername,EditPassword;
	private Button login_btn,to_menu_home;
	private CheckBox rem_pw, auto_login;
	private SharedPreferences sp;
	/**
	 * 用户登录地址
	 */
	private String LOGIN_URL = "/user/login.action";
	
	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myCount = (MyCount) getApplication();
		
		EditUsername = (EditText) findViewById(R.id.editText_username);
		EditPassword = (EditText) findViewById(R.id.editText_password);
		login_btn = (Button) findViewById(R.id.btn_login);
		to_menu_home = (Button) findViewById(R.id.to_menu_home);
		
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);
		
		//判断记住密码多选框的状态
		if(sp.getBoolean("ISCHECK", false)){
			//设置默认是记录密码状态
			rem_pw.setChecked(true);
			EditUsername.setText(sp.getString("USER_NAME", ""));
			EditPassword.setText(sp.getString("PASSWORD", ""));
			//判断自动登录多选框状态
			if(sp.getBoolean("AUTO_ISCHECK", false)){
				//设置默认是自动登录状态
				auto_login.setChecked(true);
				//跳转界面
				Intent intent = new Intent(MainActivity.this, LogoActivity.class);
				MainActivity.this.startActivity(intent);
			}
		}
		
		login_btn.setOnClickListener(loginListenner);
		to_menu_home.setOnClickListener(toMuneListenner);
		
		rem_pw.setOnClickListener(remember);
		auto_login.setOnClickListener(autoLogin);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * 点击登录按钮监听
	 */
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
	/**
	 * 点击跳转按钮监听，跳致底部菜单页
	 */
	private OnClickListener toMuneListenner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(new Intent(MainActivity.this, HomeMenuActivity.class));
		}
	};
	/**
	 * 监听记住密码
	 */
	private OnClickListener remember = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(rem_pw.isChecked()){
				System.out.println("记住密码已选中");
				sp.edit().putBoolean("ISCHECK", true).commit();
			}else {
				System.out.println("记住密码没有选中");
				sp.edit().putBoolean("ISCHECK", false).commit();
			}
		}
	};
	/**
	 * 监听自动登录
	 */
	private OnClickListener autoLogin = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(auto_login.isChecked()){
				System.out.println("自动登录已选中");
				sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
			}else {
				System.out.println("自动登录没有选中");
				sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
			}
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
	
	private void userLogin(Context context, final String username, final String password){
		System.out.println("请求的路径是=====>>"+myCount.getURL());
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost post = new HttpPost(myCount.getURL()+LOGIN_URL);
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();
					parameters.add(new BasicNameValuePair("username", username));
					parameters.add(new BasicNameValuePair("password", password));
//					post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
					
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("username", username);
					map.put("password", password);
					HttpEntity entity = new StringEntity(getJsonByMap(map, null));
					post.setEntity(entity);
					HttpResponse response = httpClient.execute(post);
					if(response.getStatusLine().getStatusCode() == 200){
						String msg = EntityUtils.toString(response.getEntity());
						System.out.println(msg);
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
							Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
							//登录成功和记住密码框为选中状态才保存用户信息
							if(rem_pw.isChecked()){
								//记住用户名、密码
								Editor editor = sp.edit();
								editor.putString("USER_NAME", username);
								editor.putString("PASSWORD", password);
								editor.commit();
							}
							
							MainActivity.this.startActivity(new Intent(MainActivity.this, LogoActivity.class));
						}else {
							Toast.makeText(MainActivity.this, "用户名或密码错误，请重新登录", Toast.LENGTH_SHORT).show();
						}
//						Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
				Looper.loop();
			}
		}).start();
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

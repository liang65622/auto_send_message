package com.justin.auto_send_message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.justin.auto_send_message.db.DataHelper;

import android.R.integer;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class SendMsgService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	DataHelper mDataHelper;

	Timer mTimer;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(DEBUG_STRING, "onStartCommand");
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			int count = 0;

			@Override
			public void run() {
				try {
					Log.d(DEBUG_STRING, "TimerTask:" + count++);
					String jsonResult = connWithPost(initSignPassM());
					JSONObject jsonObject = new JSONObject(jsonResult)
							.getJSONObject("sududa");
					Bundle bundle = new Bundle();
					bundle.putString("tips", jsonObject.getString("tips"));
					if ("1".equals(jsonObject.getString("status"))) {
						String list = jsonObject.getString("list");
						if (!list.equals("")) {
							JSONArray array = new JSONObject(list).getJSONArray("l");
							ArrayList<PhoneMessage> msgs = new ArrayList<PhoneMessage>();
							for (int i = 0; i < array.length(); i++) {
								JSONObject jo = array.getJSONObject(i);
								PhoneMessage msg = new PhoneMessage();
								msg.setPhoneNum(jo.getString("t"));
								msg.setMsg(jo.getString("c"));
								msgs.add(msg);
							}
							sendMessages(msgs);
						} else {

						}
					} else {

					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, 2 * 1000, 2 * 1000);
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onCreate() {
		super.onCreate();
		mDataHelper = new DataHelper(this);
		paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
		smsManager = SmsManager.getDefault();
	}

	PendingIntent paIntent;
	SmsManager smsManager;

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer.cancel();
	}

	private void sendMessages(ArrayList<PhoneMessage> msgs) {
		ArrayList<PhoneMessage> sendList = new ArrayList<PhoneMessage>();
		for (PhoneMessage msg : msgs) {
			String key = msg.getPhoneNum();
			String val = msg.getMsg();
			if (key != null && !key.equals("")) {
				long startTime = System.currentTimeMillis();
//				smsManager.sendTextMessage(key, null, val, paIntent, null);
				long endTime = System.currentTimeMillis();
				long useTime = endTime - startTime;
				System.out.println(new Date().toLocaleString() + "     "  + new Date().toString());
				System.out.println((new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
				PhoneMessage phoneMessage = new PhoneMessage(key.toString(),
						val.toString(), (new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()), useTime + "");
				sendList.add(phoneMessage);
				mDataHelper.addMsgs(phoneMessage);
			}
			// MsgListAdapter adapter = new MsgListAdapter(msgList, this);
		}
	}

	public static HashMap<String, String> initSignPassM() {
		HashMap<String, String> map = new HashMap<String, String>();
		int timeStamp = (int) (System.currentTimeMillis() / 1000);
		map.put("username", "send@sududa.com");
		map.put("timestamp", "" + timeStamp);
		map.put("format", "json");
		map.put("ver", "3");

		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		Collection<String> keyset = map.keySet();
		List<String> list = new ArrayList<String>(keyset);
		// 对key键值按字典升序排序
		Collections.sort(list);
		// 初始化参数
		String params = "/m/msg_list?";
		for (int i = 0; i < list.size(); i++) {
			params += list.get(i) + "=" + map.get(list.get(i)) + "&";
		}
		params += "ABC123";
		System.out.println(params);
		// 转码
		try {

			params = URLEncoder.encode(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("initSignPassParams", e.getMessage());
		}
		// 加密
		String signm = MD5.md5(params);
		// 拼接URL
		map.put("signm", signm);
		Log.d("", "initSignMParams() map:" + map.toString());
		return map;
	}

	public static String connWithPost(HashMap<String, String> map)
			throws ClientProtocolException, IOException {
		// 第一步，创建HttpPost对象

		HttpPost httpPost = new HttpPost("http://app.sududa.com/m/msg_list?");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {

				Log.d(DEBUG_STRING, " key:" + entry.getKey() + " value:"
						+ entry.getValue());

				BasicNameValuePair pair = new BasicNameValuePair(
						entry.getKey(), entry.getValue());
				pairs.add(pair);
			}
		}
		HttpResponse httpResponse = null;
		String result = "";
		// 设置httpPost请求参数
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		String urlString = httpPost.getParams().toString();
		httpResponse = new DefaultHttpClient().execute(httpPost);
		// System.out.println(httpResponse.getStatusLine().getStatusCode());

		if (httpResponse.getStatusLine().getStatusCode() == 200)
			result = EntityUtils.toString(httpResponse.getEntity());
		Log.d(DEBUG_STRING, result + "status:"
				+ httpResponse.getStatusLine().getStatusCode());
		return result;
	}

	private final static String DEBUG_STRING = "SendMsgService";
}

package com.justin.auto_send_message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

import com.justin.auto_send_message.db.DataHelper;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	PendingIntent paIntent;
	SmsManager smsManager;

	Timer mTimer;
	Activity mContext;
DataHelper mDataHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		paIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
		smsManager = SmsManager.getDefault();
		mDataHelper = new DataHelper(this);
		boolean isServiceWork = isServiceWork(this,
				"com.justin.auto_send_message.SendMsgService");
		final TextView status = (TextView) findViewById(R.id.service_status);
		status.setText(isServiceWork ? "运行中" : "未启动");
		final TextView msgCount =  (TextView) findViewById(R.id.msg_count);
		msgCount.setText(mDataHelper.getMsgsCount()+"");
		mContext = this;
		mTimer = new Timer();
		
		mTimer.schedule(new TimerTask() {
			int count = 0;

			@Override
			public void run() {
				final boolean isServiceWork = isServiceWork(MainActivity.this,
						"com.justin.auto_send_message.SendMsgService");
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						status.setText(isServiceWork ? "运行中" : "未启动");
						msgCount.setText(mDataHelper.getMsgsCount()+"");
					}
				});
				
			}
		}, 1 * 1000, 1 * 1000);
		findViewById(R.id.start_auto_send_service).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent updateIntent = new Intent(MainActivity.this,
								SendMsgService.class);
						updateIntent.putExtra("titleId", R.string.app_name);
						MainActivity.this.startService(updateIntent);
					}
				});

		findViewById(R.id.stop_auto_send_service).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent updateIntent = new Intent(MainActivity.this,
								SendMsgService.class);
						updateIntent.putExtra("titleId", R.string.app_name);
						MainActivity.this.stopService(updateIntent);
					}
				});

		findViewById(R.id.msg_list).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent msgList = new Intent(mContext, MsgList.class);
				startActivity(msgList);
			}
		});

		// final ArrayList<Message> msgList = new ArrayList<Message>();
		//
		// ListView listView = (ListView) findViewById(R.id.msg_list);
		// for (Message msg :msgs) {
		// String key = msg.getPhoneNum();
		// String val = msg.getMsg();
		// if (key != null && !key.equals("")) {
		// smsManager.sendTextMessage(key, null,
		// val, paIntent, null);
		// msgList.add(new Message(key.toString(), val.toString()));
		// }
		// }
		// MsgListAdapter adapter = new MsgListAdapter(msgList, this);
		// listView.setAdapter(adapter);
	}

	private Handler mHandler = new Handler();

	public boolean isServiceWork(Context mContext, String serviceName) {

		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = (List<RunningServiceInfo>) myAM
				.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

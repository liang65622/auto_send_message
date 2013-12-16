package com.justin.auto_send_message;

import java.util.ArrayList;

import com.justin.auto_send_message.db.DataHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MsgList extends Activity{
DataHelper mDataHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_list);
		ListView listView = (ListView) findViewById(R.id.msg_list);
		mDataHelper = new DataHelper(this);
		ArrayList<PhoneMessage> msgs = mDataHelper.getPhoneMsgs(null);
		MsgListAdapter adapter = new MsgListAdapter(msgs, this);
		listView.setAdapter(adapter);
	}
}


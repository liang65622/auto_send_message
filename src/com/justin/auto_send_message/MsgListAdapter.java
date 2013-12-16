package com.justin.auto_send_message;

import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MsgListAdapter extends BaseAdapter {

	ArrayList<PhoneMessage> msgs;
	Activity activity;
	public MsgListAdapter(ArrayList<PhoneMessage> msgs,Activity activity){
		this.msgs = msgs;
		this.activity = activity;
	}
	@Override
	public int getCount() {
		return msgs.size();
	}

	@Override
	public Object getItem(int position) {
		return msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LayoutInflater.from(activity).inflate(
					R.layout.msg_item, null);
			// holder.hardCode = (TextView) convertView
			// .findViewById(R.id.hardcode);
			holder.phoneNum = (TextView) convertView.findViewById(R.id.phone_num);
			holder.message = (TextView) convertView.findViewById(R.id.phone_msg);
			holder.sendTime = (TextView) convertView.findViewById(R.id.sendTime);
			holder.userTime  = (TextView)convertView.findViewById(R.id.useTime);
			// holder.ip = (TextView) convertView.findViewById(R.id.ip);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.phoneNum.setText(msgs.get(position).getPhoneNum());
		holder.message.setText(msgs.get(position).getMsg());
		holder.sendTime.setText(msgs.get(position).getSendTime());
		holder.userTime.setText("ºÄÊ±:" + msgs.get(position).getUseTime() + "ms");
		return convertView;
	}
	
	class ViewHolder {
		TextView phoneNum;
		TextView message;
		TextView userTime;
		TextView sendTime;
	}

}

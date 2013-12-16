package com.justin.auto_send_message;

import android.R.integer;

public class PhoneMessage {
	private String phoneNum;
	private String msg;
	private String sendTime;
	private String useTime;
	public PhoneMessage(String phoneNum, String msg, String sendTime,
			String useTime) {
		super();
		this.phoneNum = phoneNum;
		this.msg = msg;
		this.sendTime = sendTime;
		this.useTime = useTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public PhoneMessage(String phoneNum, String msg) {
		super();
		this.phoneNum = phoneNum;
		this.msg = msg;
	}
	public PhoneMessage() {
		super();
	}
	@Override
	public String toString() {
		return "PhoneMessage [phoneNum=" + phoneNum + ", msg=" + msg
				+ ", sendTime=" + sendTime + ", useTime=" + useTime + "]";
	}
	
}

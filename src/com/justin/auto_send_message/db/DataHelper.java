package com.justin.auto_send_message.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.justin.auto_send_message.PhoneMessage;

public class DataHelper {
	// 数据库名称
	// 数据库版本
	private static int DB_VERSION = 2;
	private SQLiteDatabase db;
	private MyDatabaseHelper dbHelper;

	public DataHelper(Context context) {
		if (dbHelper == null)
			dbHelper = new MyDatabaseHelper(context, MyDatabaseHelper.DB,
					DB_VERSION);
		if (db == null)
			db = dbHelper.getWritableDatabase();
	}

	public void Close() {
		if (db.isOpen())
			db.close();
		if (dbHelper != null)
			dbHelper.close();
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public MyDatabaseHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(MyDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public long addMsgs(PhoneMessage msg){
		ContentValues values = new ContentValues();
		values.put(MyDatabaseHelper.PHONE_NUM, msg.getPhoneNum());
		values.put(MyDatabaseHelper.PHONE_MESSAGE, msg.getMsg());
		values.put(MyDatabaseHelper.PHONE_SEND_TIME, msg.getSendTime());
		values.put(MyDatabaseHelper.PHONE_USE_TIME, msg.getUseTime());
	long uid= db.insert(MyDatabaseHelper.PHONE_MESSAGE_TB_NAME, null, values);
		return uid;
	}
	public ArrayList<PhoneMessage> getPhoneMsgs(String row) {
		ContentValues values = new ContentValues();
		// 构造SQLite的Content对象，这里也可以使用raw
		Cursor cursor = db.query(MyDatabaseHelper.PHONE_MESSAGE_TB_NAME,
				new String[] { MyDatabaseHelper.PHONE_NUM,
						MyDatabaseHelper.PHONE_MESSAGE,
						MyDatabaseHelper.PHONE_USE_TIME,
						MyDatabaseHelper.PHONE_SEND_TIME }, null, null, null,
				null, null);
		ArrayList<PhoneMessage> msgs = new ArrayList<PhoneMessage>();
		while (cursor.moveToNext()) {
			String phoneNum = cursor.getString(0);
			String phoneMsg = cursor.getString(1);
			String useTime = cursor.getString(2);
			String sendTime = cursor.getString(3);
			msgs.add(new PhoneMessage(phoneNum, phoneMsg, sendTime, useTime));
		}
		return msgs;
	}

	public long getMsgsCount() {
		ContentValues values = new ContentValues();
		// 构造SQLite的Content对象，这里也可以使用raw
		Cursor cursor = db.query(MyDatabaseHelper.PHONE_MESSAGE_TB_NAME,
				new String[] { MyDatabaseHelper.PHONE_NUM,
						MyDatabaseHelper.PHONE_MESSAGE,
						MyDatabaseHelper.PHONE_USE_TIME,
						MyDatabaseHelper.PHONE_SEND_TIME }, null, null, null,
				null, null);
		
		return cursor.getCount();
	}

	
	// // 更新users表的记录
	// public int UpdateUserInfo(UserInfo user) {
	// ContentValues values = new ContentValues();
	// values.put(UserInfo.USERID, user.getUserId());
	// values.put(UserInfo.TOKEN, user.getToken());
	// values.put(UserInfo.TOKENSECRET, user.getTokenSecret());
	// int id = db.update(MyDatabaseHelper.TB_NAME, values, UserInfo.USERID +
	// "="
	// + user.getUserId(), null);
	// Log.e("UpdateUserInfo", id + "");
	// return id;
	// }


}
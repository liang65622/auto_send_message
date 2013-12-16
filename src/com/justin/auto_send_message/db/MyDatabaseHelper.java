/**
 *
 */
package com.justin.auto_send_message.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description: <br/>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br/>
 * Copyright (C), 2001-2014, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
	public static final int DB_VERSION = 2;
	public static final String DB = "sududa.db3";
	public static final String PHONE_MESSAGE_TB_NAME = "phone_message";
	public static final String PHONE_NUM = "phone_num";
	public static final String PHONE_MESSAGE = "phone_message";

	public static final String PHONE_USE_TIME = "use_time";
	public static final String PHONE_SEND_TIME = "send_time";
	// final String CREATE_TABLE_SQL =
	// "create table user_login_message(_id integer primary "
	// +
	// "key autoincrement , user_name varchar(50),password varchar(50),current_account varchar(50)";

	final String CREATE_TABLE_SQL = "create table user_login_message(_id integer primary "
			+ "key autoincrement , user_name varchar(50),password varchar(50),current_account varchar(50))";

	final String CREATE_PHONE_MESSAGE_TABLE_SQL = "create table "
			+ PHONE_MESSAGE_TB_NAME + "(_id integer primary "
			+ "key autoincrement , " + PHONE_NUM + " varchar(50),"
			+ PHONE_MESSAGE + " varchar(50), " + PHONE_USE_TIME
			+ " varchar(50)," + PHONE_SEND_TIME + " varchar(50))";

	public MyDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@SuppressLint("NewApi")
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一次使用数据库时自动建表
		db.execSQL(CREATE_PHONE_MESSAGE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--------onUpdate Called--------" + oldVersion
				+ "--->" + newVersion);
	}

}

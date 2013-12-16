package com.justin.auto_send_message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint.Join;
import android.os.Environment;
import android.util.Log;

public class JsonUtil {
	private static final String TAG = "jsonUtil";
	private JSONObject jsonObject;
	private static final String KEY = "sududa";

	private JsonUtil(String json) {
		jsonObject = getJsonObject(json);
		if (jsonObject == null) {
			// Log.e(TAG, "jsonobject is null");
		}
	}

	private static JsonUtil jsonUtil;

	public static JsonUtil getJsonUtil() {
		if (jsonUtil == null)
			jsonUtil = new JsonUtil();

		return jsonUtil;
	}

	public JsonUtil() {
		super();
	}

	public int getShopSCount(JSONObject jb) throws JSONException {
		JSONArray jsonArray = jb.getJSONObject("list").getJSONArray("l");
		int count = 0;
		JSONObject shopJson;
		for (int i = 0; i < jsonArray.length(); i++) {
			shopJson = jsonArray.getJSONObject(i);
			String istrusteeship = shopJson.getString("status");
			if (istrusteeship.equals("10") || istrusteeship.equals("-10")) {
				count++;
			}
		}

		return count;
	}

	public static JsonUtil newJsonUtil(String json) {
		JsonUtil util = new JsonUtil(json);
		try {
			util = new JsonUtil(util.getString(KEY));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return util;
	}


	
	public static JsonUtil newJsonUtil1(String json) {
		JsonUtil util = new JsonUtil(json);
		return util;
	}

	/**
	 * get json object
	 * 
	 * @param json
	 *            json data
	 * @return JOSNObject
	 */
	public JSONObject getJsonObject(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			// Log.e(TAG, "create jsonobject exception");
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * get String data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return String data
	 * @throws JSONException
	 */
	public String getString(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getString(key);
		} else {
			return null;
		}

	}

	/**
	 * get String data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return int data
	 * @throws JSONException
	 */
	public int getInt(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getInt(key);
		} else {
			return -1;
		}

	}

	/**
	 * get Double data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return double data
	 * @throws JSONException
	 */
	public double getDouble(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getDouble(key);
		} else {
			return -1;
		}

	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(Class<?> c) throws Exception {
		if (jsonObject != null) {
			return getObject(c.getSimpleName().toLowerCase(), c);
		} else {
			return null;
		}
	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param key
	 *            query key
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(String key, Class<?> c) throws Exception {
		if (jsonObject != null) {
			return getObject(jsonObject, key, c);
		} else {
			return null;
		}
	}

	public Object getObject(JSONObject jsonObject, Class<?> c) throws Exception {
		return getObject(jsonObject, c.getSimpleName().toLowerCase(), c);
	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param key
	 *            query key
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(JSONObject jsonObject, String key, Class<?> c)
			throws Exception {
		// Log.e(TAG, "key ==  " + key);
		Object bean = null;

		if (jsonObject != null) {
			JSONObject jo = null;
			if (key != null) {
				jo = jsonObject.getJSONObject(key);
			} else {
				jo = jsonObject;
			}
			if (jo != null) {
				if (c.equals(null)) {
					// Log.e(TAG, "class is null");
					bean = jo.get(key);
				} else {
					bean = c.newInstance();
					Field[] fs = c.getDeclaredFields();
					for (int i = 0; i < fs.length; i++) {
						Field f = fs[i];
						f.setAccessible(true);
						Type type = f.getGenericType();
						String value = jo.getString(f.getName());
						// Log.e(TAG, f.getName() + "=" + value);
						if (type.equals(int.class)) {
							f.setInt(bean, Integer.valueOf(value));
						} else if (type.equals(double.class)) {
							f.setDouble(bean, Double.valueOf(value));
						} else {
							f.set(bean, value);
						}
					}
				}
			} else {
				// Log.e(TAG, "in jsonobject not key ");
			}
		} else {
			// Log.e(TAG, "current param jsonobject is null");
		}
		return bean;
	}

	/**
	 * This method use in jsonObject get list object
	 * 
	 * @param key
	 *            list key
	 * @param objectKey
	 *            object key
	 * @param c
	 *            object
	 * @return list
	 * @throws Exception
	 */
	public List<Object> getList(String key, Class<?> c) throws Exception {
		List<Object> list = null;
		if (jsonObject != null) {
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			if (!jsonArray.isNull(0)) {
				list = new ArrayList<Object>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsObject = jsonArray.getJSONObject(i);
					Object object = getObject(jsObject, null, c);
					list.add(object);
				}
			}
		}
		return list;
	}

	/**
	 * Test class field value
	 * 
	 * @param c
	 * @param classObject
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getFieldValue(Class<?> c, Object classObject)
			throws IllegalArgumentException, IllegalAccessException {
		StringBuffer sb = new StringBuffer();
		Field[] fs = c.getFields();
		for (int i = 0; i < fs.length; i++) {
			String s = fs[i].getName() + "=" + fs[i].get(classObject);
			sb.append(s).append("\n");
		}
		// Log.e(TAG, sb.toString());
		return sb.toString();
	}

	public static JSONObject getSududaJsonObject(String jsonResult)
			throws JSONException {
		JSONObject jsonObject = null;
		jsonObject = new JSONObject(jsonResult).getJSONObject("sududa");
		return jsonObject;
	}

	public static String getProductjson(Activity context) {

		final File productFile = new File(context.getExternalFilesDir(
				Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
				+ "/product.txt");
		String productJsonResult = readFileData(productFile.getAbsolutePath());
		return productJsonResult;
	}

	

	public static void writeFile(String json, File file) {
		// 声明一个Write对象
		Writer writer = null;
		// 通过FileWriter类来实例化Writer类的对象并以追加的形式写入
		try {
			// 声明一个File对象
			String command = "chmod 777 " + file.getAbsolutePath();
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);

			if (!file.exists())
				file.createNewFile();

			writer = new FileWriter(file, true);
			// 声明一个要写入的字符串
			String str = json;
			// 写入文本文件中
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFileData(String file) {
		// 也可以用String fileName = "mnt/sdcard/Y.txt";
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(file);

			// FileInputStream fin = openFileInput(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];

			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return res;

	}

}

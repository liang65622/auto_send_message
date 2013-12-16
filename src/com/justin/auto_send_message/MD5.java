package com.justin.auto_send_message;

import java.security.MessageDigest;

public class MD5 {
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 �ļ�������һ�� 128 λ�ĳ�������
										// ���ֽڱ�ʾ���� 16 ���ֽ�
			char str[] = new char[16 * 2]; // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ���
											// ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
			int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
			for (int i = 0; i < 16; i++) { // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
											// ת���� 16 �����ַ���ת��
				byte byte0 = tmp[i]; // ȡ�� i ���ֽ�
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ��и� 4 λ������ת��,
															// >>>
															// Ϊ�߼����ƣ�������λһ������
				str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ��е� 4 λ������ת��
			}
			s = new String(str); // ����Ľ��ת��Ϊ�ַ���

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String md5(String str, String encodeing) {
		MessageDigest md5;
		StringBuffer md5StrBuff = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			if (encodeing != null && !"".equals(encodeing)) {
				md5.update(str.getBytes(encodeing));
			} else {
				md5.update(str.getBytes());
			}
			byte[] domain = md5.digest();
			for (int i = 0; i < domain.length; i++) {
				if (Integer.toHexString(0xFF & domain[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & domain[i]));
				} else
					md5StrBuff.append(Integer.toHexString(0xFF & domain[i]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5StrBuff.toString().toLowerCase();
	}

	
	public static String md5_pass(String str)
	{
		return md5(str + ",vs/..~", "").toLowerCase();
	}

	/**
	 * ֧������MD5(˽��)
	 * 
	 * @param str
	 *            ��Ҫ���ܵ��ַ���
	 * **/
	public static String md5_payment(String str)
	{
		return md5(str + "#@$*)!", "").toLowerCase();
	}
	
	/**
	 * ��Ĭ�ϱ���MD5
	 * 
	 * @param str
	 *            ��Ҫ���ܵ��ַ���
	 * **/
	public static String md5(String str) {
		return md5(str, "");
	}

	public static void main(String xu[]) { // ���� "a" �� MD5
											// ���룬Ӧ��Ϊ��0cc175b9c0f1b6a831c399e269772661
		System.out.println(MD5.getMD5("qjCultureHr".getBytes()));
	}

}
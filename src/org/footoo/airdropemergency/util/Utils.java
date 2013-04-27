package org.footoo.airdropemergency.util;

public abstract class Utils {
	/**
	 * 把int型的IP地址转换为可读的十进制IP
	 * 
	 * @param ip
	 */
	public static String int2Ip(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strIsEmpty(String str) {
		return str.equals("") || str == null;
	}
}

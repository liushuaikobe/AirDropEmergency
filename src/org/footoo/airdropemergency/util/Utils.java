package org.footoo.airdropemergency.util;

import java.io.File;
import java.util.ArrayList;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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

	/**
	 * 把一个文件数组转换成ArrayList
	 * 
	 * @param fileArray
	 * @return
	 */
	public static ArrayList<File> toArrayList(File[] fileArray) {
		ArrayList<File> list = new ArrayList<File>();
		for (File f : fileArray) {
			list.add(f);
		}
		return list;
	}

	/**
	 * 判断wifi是否连接
	 * 
	 * @param info
	 * @return
	 */
	public static boolean isWifiConnected(WifiManager wifiManager,
			WifiInfo wifiInfo) {
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		return wifiManager.isWifiEnabled() && ipAddress != 0;
	}
}

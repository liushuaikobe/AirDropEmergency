package org.footoo.airdropemergency.util;

import android.content.Context;
import android.widget.Toast;

public abstract class ToastUtil {
	/**
	 * ��ʾ��ʱ���Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showLongToast(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʾ��ʱ���Toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showShortToast(Context context, CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
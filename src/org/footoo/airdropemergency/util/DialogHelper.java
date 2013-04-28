package org.footoo.airdropemergency.util;

import org.footoo.airdropemergency.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHelper {

	/**
	 * 显示确认对话框
	 * 
	 * @param context
	 * @param dialogOperationDone
	 * @param title
	 *            对话框标题
	 * @param Message
	 *            对话框消息体
	 */
	public static void showConfirmDialog(Context context,
			DialogHelper.DialogOperationDone dialogOperationDone, String title,
			String Message) {
		final DialogOperationDone fDialogOperationDone = dialogOperationDone;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(Message);
		builder.setPositiveButton(
				context.getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						fDialogOperationDone.ok();
					}
				});
		builder.setNegativeButton(context.getResources()
				.getString(R.string.cln),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						fDialogOperationDone.cancle();
					}
				});
		builder.create().show();
	}

	/**
	 * 对话框确定、取消时的操作
	 * 
	 * @author liushuai
	 * 
	 */
	public interface DialogOperationDone {
		/**
		 * 点确定的操作
		 */
		public void ok();

		/**
		 * 点取消的操作
		 */
		public void cancle();
	}
}

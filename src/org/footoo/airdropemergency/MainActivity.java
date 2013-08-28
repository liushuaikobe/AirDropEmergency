package org.footoo.airdropemergency;

import java.util.Timer;
import java.util.TimerTask;

import org.footoo.airdropemergency.constvalue.ConstValue;
import org.footoo.airdropemergency.httpserver.ServerRunner;
import org.footoo.airdropemergency.util.FileAccessUtil;
import org.footoo.airdropemergency.util.ToastUtil;
import org.footoo.airdropemergency.util.Utils;

import com.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends BaseActivity {

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	private Timer tExit;
	private TimerTask task;

	private Handler handler;

	private String IPAddr;
	private WifiManager wifiManager;
	private WifiInfo wifiInfo;
	private boolean wifiIsAvailable = false;

	private Fragment mContent;

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		if (mContent == null) {
			mContent = new MainFragment();
		}

		// set the above view
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the behind view
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new BehindMenuFragment()).commit();

		// customize the slidemenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		try {
			initData();
		} catch (Exception e) {
			ToastUtil.showShortToast(MainActivity.this,
					getString(R.string.unknown_error));
		}
	}

	private void initData() {
		// var used for double click to exit
		tExit = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				isExit = false;
				hasTask = true;
			}
		};

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// get the ip address successfully, start the server
				Bundle bundle = msg.getData();
				Log.i("ip", bundle.getString("ip") + "");
				IPAddr = bundle.getString("ip");
				// update the address in main fragment
				if (mContent instanceof MainFragment) {
					((MainFragment) mContent).setAddrTvText(getBrowserAddr());
				}
				// start the server
				ServerRunner.startServer(ConstValue.PORT);
				// TODO notifiction
			}
		};

		// create the dir to store the received files
		ConstValue.BASE_DIR = FileAccessUtil.createDir(ConstValue.DIR_NAME);

		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		// wifi is available?
		wifiIsAvailable = Utils.isWifiConnected(wifiManager, wifiInfo);
		if (wifiIsAvailable) {
			// wifi is available, get the ip address in a new thread
			new Thread(new Runnable() {

				@Override
				public void run() {
					Bundle bundle = new Bundle();
					bundle.putString("ip",
							Utils.int2Ip(wifiInfo.getIpAddress()));
					Message msg = new Message();
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
			}).start();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				ToastUtil
						.showShortToast(
								this,
								getResources().getString(
										R.string.double_click_to_exit));
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				ServerRunner.stopServer();
				finish();
				System.exit(0);
			}
		}
		return false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * called by the behind menu fragment
	 * 
	 * @param fragment
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * called by the Main Fragment
	 * 
	 * @return
	 */
	public String getBrowserAddr() {
		if (wifiIsAvailable) {
			return "http://" + IPAddr + ":" + ConstValue.PORT;
		} else {
			return "";
		}
	}

	/**
	 * called by the Main Fragment
	 * 
	 * @return
	 */
	public String getBrowserPrompt() {
		int promptId = wifiIsAvailable ? R.string.input_on_browser
				: R.string.wifi_not_avaliable;
		return getString(promptId);
	}
}

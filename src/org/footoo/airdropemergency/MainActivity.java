package org.footoo.airdropemergency;

import java.util.Timer;
import java.util.TimerTask;

import org.footoo.airdropemergency.httpserver.ServerRunner;
import org.footoo.airdropemergency.util.ToastUtil;

import com.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class MainActivity extends BaseActivity {

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	private Timer tExit = new Timer();
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

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

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
}

package org.footoo.airdropemergency;

import java.io.IOException;

import org.footoo.airdropemergency.httpserver.ServerRunner;
import org.footoo.airdropemergency.httpserver.SimpleFileServer;
import org.footoo.airdropemergency.util.Utils;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Context mContext = MainActivity.this;
	private Handler handler;

	private WifiManager wifiManager;
	private WifiInfo wifiInfo;

	private TextView addrTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			initData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initViews();

//		 ServerRunner.run(SimpleFileServer.class);
		try {
			new SimpleFileServer(51345).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initViews() {
		addrTv = (TextView) findViewById(R.id.addr_tv);
	}

	@SuppressLint("HandlerLeak")
	private void initData() throws IOException {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				addrTv.setText(bundle.getString("ip") + ":" + 51345);
			}
		};

		wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();

		new Thread(runnable).start();
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Bundle bundle = new Bundle();
			bundle.putString("ip", Utils.int2Ip(wifiInfo.getIpAddress()));
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};

}

package org.footoo.airdropemergency;

import java.io.IOException;

import org.footoo.airdropemergency.constvalue.ConstValue;
import org.footoo.airdropemergency.httpserver.ServerRunner;
import org.footoo.airdropemergency.util.FileAccessUtil;
import org.footoo.airdropemergency.util.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

	private Context mContext;

	private Handler handler;

	private WifiManager wifiManager;
	private WifiInfo wifiInfo;

	private TextView addrTv;
	private TextView infoTv;

	private View mainView;

	private static final int PORT = 51345;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.activity_main, null);
		mContext = getActivity();
		try {
			initViews();
			initData();
			// 启动服务器
			ServerRunner.startServer(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mainView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 关闭服务器
		ServerRunner.stopServer();
	}

	private void initViews() {
		addrTv = (TextView) mainView.findViewById(R.id.addr_tv);
		infoTv = (TextView) mainView.findViewById(R.id.main_info_tv);
	}

	@SuppressLint("HandlerLeak")
	private void initData() throws IOException {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				Log.i("ip", bundle.getString("ip") + "");
				addrTv.setText("http://" + bundle.getString("ip") + ":" + PORT);
			}
		};

		wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();

		ConstValue.BASE_DIR = FileAccessUtil.createDir(ConstValue.DIR_NAME);

		// 判断wifi是否可用
		if (!Utils.isWifiConnected(wifiManager, wifiInfo)) {
			infoTv.setText(mContext.getResources().getString(
					R.string.wifi_not_avaliable));
			return;
		}

		infoTv.setText(mContext.getResources().getString(
				R.string.input_on_browser));

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

package org.footoo.airdropemergency;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

@SuppressLint("ValidFragment")
public class MainFragment extends SherlockFragment {
	private TextView addrTv;
	private TextView promptTv;

	private View mainView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.activity_main, null);
		initViews();
		initData();
		return mainView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initViews() {
		addrTv = (TextView) mainView.findViewById(R.id.addr_tv);
		promptTv = (TextView) mainView.findViewById(R.id.main_info_tv);
	}

	private void initData() {
		if (getActivity() instanceof MainActivity) {
			addrTv.setText(((MainActivity) getActivity()).getBrowserAddr());
			promptTv.setText(((MainActivity) getActivity()).getBrowserPrompt());
		}
	}

	public void setAddrTvText(CharSequence txt) {
		addrTv.setText(txt);
	}

	public void setPromptTvText(CharSequence txt) {
		promptTv.setText(txt);
	}
}

package org.footoo.airdropemergency;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BehindMenuFragment extends ListFragment {

	private Resources res;
	private String[] menuStrings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		res = getActivity().getResources();
		menuStrings = new String[] { res.getString(R.string.main),
				res.getString(R.string.have_uploaded),
				res.getString(R.string.config), res.getString(R.string.about) };
		ArrayAdapter<String> mainAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, menuStrings);
		setListAdapter(mainAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new MainFragment();
			break;
		case 1:
			newContent = new FileViewFragment();
			break;
		case 2:
			newContent = new ConfigFragment();
			break;
		case 3:
			newContent = new AboutFragment();
			break;
		}
		if (newContent != null) {
			switchFragment(newContent);
		}
	}

	/**
	 * switch the above fragment
	 * 
	 * @param content
	 */
	private void switchFragment(Fragment content) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(content);
		}
	}
}

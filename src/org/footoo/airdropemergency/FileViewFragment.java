package org.footoo.airdropemergency;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.footoo.airdropemergency.constvalue.ConstValue;
import org.footoo.airdropemergency.util.FileAccessUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class FileViewFragment extends Fragment {

	private File[] fileList;

	private View mainView;
	private ViewSwitcher switcher;
	private ListView fileLv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.file_view, null);
		switcher = (ViewSwitcher) mainView
				.findViewById(R.id.file_view_switcher);
		fileLv = (ListView) mainView.findViewById(R.id.file_list);
		new ScanFileTask().execute("begin");
		return mainView;
	}

	private class ScanFileTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			switcher.setDisplayedChild(0);
		}

		@Override
		protected String doInBackground(String... params) {
			fileList = FileAccessUtil.getFillList(ConstValue.BASE_DIR);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			fileLv.setAdapter(new FileLvAdapter(fileList));
			fileLv.setOnItemClickListener(fileLvItemClickListener);
			switcher.showNext();
		}
	}

	private AdapterView.OnItemClickListener fileLvItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> views, View view, int position,
				long id) {
			FileAccessUtil.viewFile(fileList[position].getAbsolutePath(),
					getActivity());
		}
	};

	/**
	 * 显示文件列表的Adapter
	 * 
	 * @author liushuai
	 * 
	 */
	private class FileLvAdapter extends BaseAdapter {

		private File[] files;
		private SimpleDateFormat simpleDateFormat;

		public FileLvAdapter(File[] files) {
			this.files = files;
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

		@Override
		public int getCount() {
			return files.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.file_list_item, null);
				holder = new ItemHolder();
				holder.fileName_tv = (TextView) convertView
						.findViewById(R.id.file_title);
				holder.fileLastModifyTime_tv = (TextView) convertView
						.findViewById(R.id.file_lat_modify_time);
				convertView.setTag(holder);
			} else {
				holder = (ItemHolder) convertView.getTag();
			}
			holder.fileName_tv.setText(files[position].getName());
			holder.fileLastModifyTime_tv.setText(simpleDateFormat
					.format(new Date(files[position].lastModified())));
			return convertView;
		}

		private class ItemHolder {
			public TextView fileName_tv;
			public TextView fileLastModifyTime_tv;
		}

	}
}

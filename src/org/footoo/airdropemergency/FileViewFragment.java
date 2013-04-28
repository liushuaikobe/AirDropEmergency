package org.footoo.airdropemergency;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.footoo.airdropemergency.constvalue.ConstValue;
import org.footoo.airdropemergency.util.DialogHelper;
import org.footoo.airdropemergency.util.DialogHelper.DialogOperationDone;
import org.footoo.airdropemergency.util.FileAccessUtil;
import org.footoo.airdropemergency.util.ToastUtil;
import org.footoo.airdropemergency.util.Utils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class FileViewFragment extends Fragment {

	private ArrayList<File> fileList;

	private View mainView;
	private ViewSwitcher switcher;
	private ListView fileLv;

	private BaseAdapter fileLvAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.file_view, null);
		switcher = (ViewSwitcher) mainView
				.findViewById(R.id.file_view_switcher);
		fileLv = (ListView) mainView.findViewById(R.id.file_list);
		setHasOptionsMenu(true);
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
			fileList = Utils.toArrayList(FileAccessUtil
					.getFillList(ConstValue.BASE_DIR));
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			fileLvAdapter = new FileLvAdapter(fileList);
			fileLv.setAdapter(fileLvAdapter);
			fileLv.setOnItemClickListener(fileLvItemClickListener);
			fileLv.setOnItemLongClickListener(fileLvItemLongClickListener);
			switcher.showNext();
		}
	}

	/**
	 * 文件查看的ListView的点击事件
	 */
	private AdapterView.OnItemClickListener fileLvItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> views, View view, int position,
				long id) {
			FileAccessUtil.viewFile(fileList.get(position).getAbsolutePath(),
					getActivity());
		}
	};

	/**
	 * 文件查看的ListView的长按事件
	 */
	private AdapterView.OnItemLongClickListener fileLvItemLongClickListener = new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> views, View view,
				int position, long id) {
			final int fPosition = position;
			DialogHelper.showConfirmDialog(
					getActivity(),
					new DialogOperationDone() {

						@Override
						public void ok() {
							try {
								fileList.get(fPosition).delete();
								fileList.remove(fPosition);
								fileLvAdapter.notifyDataSetChanged();
								ToastUtil.showShortToast(
										getActivity(),
										getActivity().getResources().getString(
												R.string.delete_success));
							} catch (Exception e) {
								ToastUtil.showShortToast(
										getActivity(),
										getActivity().getString(
												R.string.delete_fail));
								e.printStackTrace();
							}
						}

						@Override
						public void cancle() {
							// do nothing
						}
					},
					getActivity().getResources().getString(R.string.confirm),
					getActivity().getResources().getString(
							R.string.confirm_dialog_msg));
			return true;
		}
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			DialogHelper.showConfirmDialog(
					getActivity(),
					new DialogOperationDone() {

						@Override
						public void ok() {
							try {
								for (File file : fileList) {
									file.delete();
								}
								fileList.clear();
								fileLvAdapter.notifyDataSetChanged();
								ToastUtil.showShortToast(
										getActivity(),
										getActivity().getString(
												R.string.delete_success));
							} catch (Exception e) {
								e.printStackTrace();
								ToastUtil.showShortToast(
										getActivity(),
										getActivity().getString(
												R.string.delete_fail));
							}
						}

						@Override
						public void cancle() {
							// do nothing...
						}
					},
					getActivity().getResources().getString(R.string.confirm),
					getActivity().getResources().getString(
							R.string.confirm_delete_all_files));
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * 显示文件列表的Adapter
	 * 
	 * @author liushuai
	 * 
	 */
	private class FileLvAdapter extends BaseAdapter {

		private ArrayList<File> files;
		private SimpleDateFormat simpleDateFormat;

		public FileLvAdapter(ArrayList<File> files) {
			this.files = files;
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

		@Override
		public int getCount() {
			return files.size();
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
			holder.fileName_tv.setText(files.get(position).getName());
			holder.fileLastModifyTime_tv.setText(simpleDateFormat
					.format(new Date(files.get(position).lastModified())));
			return convertView;
		}

		private class ItemHolder {
			public TextView fileName_tv;
			public TextView fileLastModifyTime_tv;
		}

	}
}

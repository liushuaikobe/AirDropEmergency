package org.footoo.airdropemergency.util;

import java.io.File;

import android.os.Environment;

public abstract class FileAccessUtil {
	/**
	 * 判断是否有SD卡
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 根据文件夹名字返回一个合适的完整的存储路径（SD卡或者文件系统根目录）
	 * 
	 * @param dirName
	 * @return
	 */
	public static String getDirBasePath(String dirName) {
		if (FileAccessUtil.hasSdcard()) {
			return "/sdcard" + "/" + dirName;
		} else {
			return "/" + dirName;
		}
	}

	/**
	 * 创建名字为dir的目录
	 * 
	 * @param dir
	 * @return 返回目录的完整路径
	 */
	public static String createDir(String dir) {
		String fullPath = getDirBasePath(dir);
		File f = new File(fullPath);
		if (!f.exists()) {
			f.mkdir();
		}
		return fullPath;
	}

	/**
	 * 根据路径创建文件
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static File getFile(String fileFullPath) {
		File f = new File(fileFullPath);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (Exception e) {
				return null;
			}
		}
		return f;
	}

	/**
	 * 解析FileName字段得到文件名字
	 * 
	 * @param rawName
	 * @return
	 */
	public static String decodeFileName(String rawName) {
		String fileName = rawName;
		if (fileName.contains("\\")) {
			fileName = fileName.substring(fileName.lastIndexOf("\\"));
		}
		return fileName;
	}

	/**
	 * 返回一个文件夹下的所有文件
	 * 
	 * @param dirPath
	 * @return
	 */
	public static File[] getFillList(String dirPath) {
		File file = new File(dirPath);
		return file.listFiles();
	}
}

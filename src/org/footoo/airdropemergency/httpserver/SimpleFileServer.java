package org.footoo.airdropemergency.httpserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.footoo.airdropemergency.constvalue.HtmlConst;
import org.footoo.airdropemergency.util.FileAccessUtil;

public class SimpleFileServer extends NanoHTTPD {

	private String basePath;
	private static final String DIR_NAME = "MyAirDrop";

	public SimpleFileServer(int port) {
		super(51345);
		basePath = FileAccessUtil.createDir(DIR_NAME);
	}

	@Override
	public Response serve(String uri, Method method,
			Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		if (Method.GET.equals(method)) {
			return new Response(HtmlConst.HTML_STRING);
		}

		for (String s : files.keySet()) {
			try {
				FileInputStream fis = new FileInputStream(files.get(s));
				FileOutputStream fos = new FileOutputStream(
						FileAccessUtil.getFile(basePath + "/" + "1.docx"));
				byte[] buffer = new byte[1024];
				while (true) {
					int byteRead = fis.read(buffer);
					if (byteRead == -1) {
						break;
					}
					fos.write(buffer, 0, byteRead);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new Response(files.keySet() + "");
	}

}

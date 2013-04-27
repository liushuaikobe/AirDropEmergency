package org.footoo.airdropemergency.httpserver;

import java.util.Map;

public class SimpleFileServer extends NanoHTTPD {

	public SimpleFileServer(int port) {
		super(51345);
	}

	@Override
	public Response serve(String uri, Method method,
			Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		return new Response("It works!!");
	}

}

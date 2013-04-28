package org.footoo.airdropemergency.httpserver;

import java.io.IOException;

public class ServerRunner {
	private static SimpleFileServer server;

	/**
	 * 在指定端口启动server
	 * 
	 * @param port
	 */
	public static void startServer(int port) {
		server = SimpleFileServerFactory.getInstance(port);
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭服务器
	 */
	public static void stopServer() {
		if (server != null) {
			server.stop();
		}
	}
}
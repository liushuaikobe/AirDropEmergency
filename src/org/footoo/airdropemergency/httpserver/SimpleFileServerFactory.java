package org.footoo.airdropemergency.httpserver;

/**
 * 实现SimpleFileServer的单例模式
 * 
 * @author liushuai
 * 
 */
public class SimpleFileServerFactory {
	private static SimpleFileServer server;

	/**
	 * 获取一个SimpleFileServer的实例
	 * 
	 * @return
	 */
	public static SimpleFileServer getInstance(int port) {
		if (server == null) {
			server = new SimpleFileServer(port);
		}
		return server;
	}
}

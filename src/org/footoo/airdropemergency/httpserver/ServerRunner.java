package org.footoo.airdropemergency.httpserver;

import java.io.IOException;

import android.util.Log;

public class ServerRunner {
	public static void run(Class serverClass) {
		try {
			executeInstance((NanoHTTPD) serverClass.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void executeInstance(NanoHTTPD server) {
		try {
			server.start();
			Log.i("air", "server start..");
		} catch (IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
			System.exit(-1);
		}

		System.out.println("Server started, Hit Enter to stop.\n");

		try {
			System.in.read();
		} catch (Throwable ignored) {
		}

		server.stop();
		System.out.println("Server stopped.\n");
	}
}
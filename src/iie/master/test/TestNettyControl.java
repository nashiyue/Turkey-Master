package iie.master.test;

import iie.net.netty.ControlServer;
import iie.net.netty.ControlServerMessagePool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestNettyControl {

	public static void main(String[] args) throws IOException {
		ControlServer server = new ControlServer(8080);
		server.start();
		System.out.println("Server start............");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while ((line = br.readLine()) != null) {
			ControlServerMessagePool.push(line);
		}
		br.close();
	}
}

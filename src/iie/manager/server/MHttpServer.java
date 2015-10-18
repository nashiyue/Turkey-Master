package iie.manager.server;

import iie.master.preference.Preference;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class MHttpServer {

	private Server server;
	private ServerConnector connector;

	public MHttpServer(String host, int port, int timeout) {
		server = new Server();
		connector = new ServerConnector(server);
		connector.setHost(host);
		connector.setPort(port);
		connector.setIdleTimeout(timeout);
		server.addConnector(connector);
		server.setHandler(new MHttpHandler());
	}

	public MHttpServer(String host, int port) {
		this(host, port, Preference.getTimeOut());
	}

	public MHttpServer(int port) {
		this("localhost", port);
	}

	public MHttpServer() {
		this(Preference.getWebPort());
	}

	public void start() throws Exception {
		server.start();
		server.join();
	}
	
	public void close(){
		connector.close();
	}
	
	public static void main(String[] args) throws Exception {
		MHttpServer server = new MHttpServer(8080);
		server.start();
	}
}

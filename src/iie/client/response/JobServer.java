package iie.client.response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import iie.master.preference.Preference;

public class JobServer extends Thread{

	private int port;
	private boolean isRun;
	private ServerSocket serverSocket;
	
	private void init() throws IOException{
		System.out.println("Master accept client request in port:" + port);
		isRun = true;
		serverSocket = new ServerSocket(port);
	}
	
	public JobServer() throws IOException {
		port = Preference.getClientPort();
		init();
	}
	
	public JobServer(int port) throws IOException{
		this.port = port;
		init();
	}

	@Override
	public void run() {
		while(isRun){
			try {
				Socket socket = serverSocket.accept();
				JobSocketThreadPool.execute(new JobSocketThread(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		JobServer server = new JobServer();
		server.start();
	}
	
}

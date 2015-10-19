package iie.client.response;

import iie.master.preference.Preference;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * １．Master接收client发来的xml文件的对象流 ２．Ｍaster接收client传来的jar文件
 * */
public class JobSocketThread implements Runnable {

	private Socket socket;

	public JobSocketThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			long id = inputStream.readLong();
			String name = inputStream.readUTF();
			System.out.println("receive file:"+name+"....");
			String path = Preference.getTmpPath()+File.separator+id;
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream(path+File.separator+name);
			int len = -1;
			byte[] buffer = new byte[8096];
			while(true){
				len = inputStream.read(buffer);
				if(len == -1){
					break;
				}
				else{
					outputStream.write(buffer, 0, len);
				}
			}
			outputStream.flush();
			outputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package iie.net.netty;

import io.netty.channel.Channel;

/**
 * TODO
 * @author liuwei
 * Time: 2015年11月6日 下午1:50:54
 */
public class ControlServerChannelManager {
//	private static HashMap<String, SocketChannel> maps = new HashMap<>();
	
	public static boolean send(ControlMessage message) {
		boolean isSuccess = false;
		if(message == null)  return isSuccess;
		for(Channel ch : ControlServerHandler.channels) {
			String[] arrays = ch.remoteAddress().toString().split(":");
			if(arrays[0].equals(message.getTo())) {
				ch.writeAndFlush(message+"\r\n");
				isSuccess = true;
			}
		}
		if(!isSuccess) {
			for(Channel ch : ControlServerHandler.channels) {
				System.out.println("on line: ["+ch.remoteAddress()+"]");
			}
		}
		return isSuccess;
	}
	
//	public static void main(String[] args) {
//		String line = "/192.168.11.52:8080";
//		String[] array = line.split(":");
//		System.out.println(array[0]);
//	}
}

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
			if(ch.remoteAddress().toString().equals(message.getTo())) {
				ch.writeAndFlush(message+"\r\n");
				System.out.println("server write and flush:"+message);
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
	
}

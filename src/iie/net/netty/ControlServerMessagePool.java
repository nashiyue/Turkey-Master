package iie.net.netty;

import java.util.LinkedList;

/**
 * TODO
 * 
 * @author liuwei Time: 2015年11月6日 下午1:10:10
 */
public class ControlServerMessagePool {
	private static LinkedList<String> queue = new LinkedList<>();

	public static synchronized String pop() {
		String content = queue.poll();
		if(content != null) {
			System.out.println("server pop:"+content);
		}
		return content;
	}

	public static synchronized void push(String msg) {
		queue.add(msg);
		System.out.println("Server push:"+msg);
	}

	public static synchronized boolean isEmpty() {
		return queue.isEmpty();
	}
}

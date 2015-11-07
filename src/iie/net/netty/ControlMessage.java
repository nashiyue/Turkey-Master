package iie.net.netty;

/**
 * TODO
 * @author liuwei
 * Time: 2015年11月6日 下午1:20:20
 */
public class ControlMessage {
	public static final int TYPE_QUIT = 0;
	public static final int TYPE_NORMAL_INFO = 1;
	public static final int TYPE_TASK_CONTROL = 2;
	public static final int TYPE_SERVER_FILES = 100;
	public static final int TYPE_CLIENT_FILES = -100;
	
	private String from;
	private String to;
	private int type;
	private String content;

	public static ControlMessage getInstance(String toStringContent) throws Exception {
		if(toStringContent == null) return null;
		String[] array=toStringContent.split(";");
		if(array.length == 4) {
			ControlMessage message = new ControlMessage();
			for(int i=0; i<4;i++) {
				String[] kv = array[i].split("=");
				if(kv.length == 2) {
					switch (kv[0]) {
					case "from":
						message.setFrom(kv[1]);
						break;
					case "to":
						message.setTo(kv[1]);
						break;
					case "type":
						message.setType(Integer.valueOf(kv[1]));
						break;
					case "content":
						message.setContent(kv[1]);
						break;
					default:
						throw new Exception("Error:"+toStringContent);
					}
				}
				else {					
					throw new Exception("Error:"+toStringContent);
				}
			}
			return message;
		}
		else {
			throw new Exception("Error:"+toStringContent);
		}
	}
	
	public ControlMessage() {
		
	}
	
	public ControlMessage(String from, String to, int type, String content) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.content = content;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "type="+type+";from="+from+";to="+to+";content="+content;
	};
	
	public static void main(String[] args) throws Exception {
		System.out.println(ControlMessage.getInstance("type=1;from=Server;to=blade1;content=gvhv;"));
	}
}
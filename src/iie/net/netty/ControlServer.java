package iie.net.netty;

import iie.master.preference.Preference;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * TODO
 * 
 * @author liuwei Time: 2015年11月6日 上午10:43:59
 */
public class ControlServer extends Thread{

	public static final String QUIT="quit";
	public static final String BLADE_NAME = "CONTROL_SERVER";
	
	private int port;
	private boolean isRun;
	
	public ControlServer(){
		this(Preference.getMasterControlPort());
	}
	
	public ControlServer(int port){
		this.port = port;
		isRun = true;
	}
	
	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
					.option(ChannelOption.SO_BACKLOG, 100)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new StringEncoder());
							ch.pipeline().addLast(new ControlServerHandler());
							System.out.println("Client "+ch.remoteAddress()+" ...");
						}
					});
			ChannelFuture future = bootstrap.bind(port).sync();
			while(isRun) {
				String msg = ControlServerMessagePool.pop();
				if(msg != null) {
					try {
						ControlMessage message = ControlMessage.getInstance(msg);
						System.out.println("Server execute:"+msg);
						if(message.getType() == ControlMessage.TYPE_QUIT && message.getTo().equals(ControlServer.BLADE_NAME)) {
							isRun = false;
						}
						else {
							System.out.println("server send:"+msg+"......");
							boolean flag = ControlServerChannelManager.send(message);
							if(flag) {
								System.out.println("Server send success....");
							}
							else {
								System.out.println("Server send failed ...");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		int port = 8080;
		new ControlServer(port).start();
	}
}

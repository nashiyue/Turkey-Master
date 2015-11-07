package iie.net.netty;

import iie.master.preference.Preference;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TODO
 * @author liuwei
 * Time: 2015年11月3日 下午8:42:01
 */
public class DownloadServer extends Thread {
	private int port;
	
	public DownloadServer(){
		this(Preference.getMasterUploadPort());
	}
	
	public DownloadServer(int port) {
		this.port = port;
	}
	
	private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
	
	public void run() {
		try {
			startServer();
		} catch(Exception e) {
			System.out.println("数据服务启动出现异常："+e.toString());
			e.printStackTrace();
		}
	}
	
	private void startServer() throws Exception {
		bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            
            b.group(bossGroup, workerGroup);
            
			b.option(ChannelOption.TCP_NODELAY, true);
			b.option(ChannelOption.SO_TIMEOUT, 60000);
            b.option(ChannelOption.SO_SNDBUF, 1048576*200);
            
            b.option(ChannelOption.SO_KEEPALIVE, true);
			
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new DownloadServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            
            System.out.println("数据服务："+port+"启动完成...");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
	
//	public static void main(String[] args) {
//		new DownloadServer(8080).start();
//	}
}

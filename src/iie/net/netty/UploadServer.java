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
 * Time: 2015年11月6日 下午9:29:39
 */
public class UploadServer extends Thread {
	
	//单实例
	private static UploadServer dbServer = null;
	
	private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    
    private UploadServer(){
    	this.port = Preference.getMasterUploadPort();
    }
    
    private UploadServer(int port){
    	this.port = port;
    }
    
    private int port;
    
	//创建实例
	public static UploadServer newBuild() {
		if(dbServer == null) {
			dbServer = new UploadServer();
		}
		return dbServer;
	}
	
	public static UploadServer newBuild(int port) {
		if(dbServer == null) {
			dbServer = new UploadServer(port);
		}
		return dbServer;
	}
	
	
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
            b.childHandler(new UploadServerInitializer());

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
	
	public static void main(String[] args) {
		System.out.println("start server.....");
//		new DBServer().start();
		UploadServer.newBuild().start();
	}
}
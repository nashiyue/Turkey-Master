package iie.master.test;

import iie.client.response.JobServer;
import iie.info.manager.JobConsumerThread;
import iie.master.preference.Preference;
import iie.net.netty.ControlServer;
import iie.net.netty.DownloadServer;
import iie.net.netty.UploadServer;
import iie.xml.reader.MasterXmlReader;

import java.io.IOException;

public class MainIndex {
	
	public static void initMaster(String masterXMl) throws Exception{
		MasterXmlReader reader = new MasterXmlReader();
		reader.init(masterXMl);
		Preference.show();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("java.class.path"));
		if(args.length < 1){
			System.out.println("Input Master conf path");
			return;
		}
		
		boolean isInitSuccessful = false;
		try {
//			initMaster("conf/conf.xml");
			initMaster(args[0]);
			isInitSuccessful = true;
		} catch (Exception e) {
			System.out.println("Master 配置文件出错...."+e.getMessage());
		}
		
//		JobXmlReader reader = new JobXmlReader();
//		try {
//			reader.init("conf/job.xml");
//			reader.getJob();
//			reader.showJob();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		//初始化成功之后，将几个监听端口起开
		if(isInitSuccessful){
			//打开监听客户端发来的Job信息请求
			JobServer jobServer = new JobServer();
			jobServer.start();
			
			JobConsumerThread consumerThread = new JobConsumerThread();
			consumerThread.start();
			
			//打开监听web 发来的查看统计信息请求
//			MHttpServer httpServer = new MHttpServer();
//			try {
//				httpServer.start();
//			} catch (Exception e) {
//				System.out.println("HttpServer start failed:"+e.getMessage());
//			}
			//打开和Slave节点的控制信息
			ControlServer controlServer = new ControlServer(Preference.getMasterControlPort());
			controlServer.start();
			//打开和slave交互的 task下发端口
			DownloadServer downloadServer = new DownloadServer();
			downloadServer.start();
			//打开和slave交互的 result 上传端口
			UploadServer uploadServer = UploadServer.newBuild(Preference.getMasterUploadPort());
			uploadServer.start();
		}
		
	}
}

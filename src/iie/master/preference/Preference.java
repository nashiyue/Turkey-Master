package iie.master.preference;

import java.io.File;

public class Preference {
	private Preference(){}	
	
	public static final String MASTER_ID = "master";
	
	private static final String DEFAULT_BASE_PATH = System.getProperty("user.home")+File.separator+"TurkeyMaster";
	private static final String DEFAULT_RESULT_PATH = DEFAULT_BASE_PATH+File.separator+"results";
	private static final String DEFAULT_LOG_PATH = DEFAULT_BASE_PATH+File.separator+"logs";
	private static final String DEFAULT_TMP_PATH = DEFAULT_BASE_PATH+File.separator+"tmps";
	
	private static final int DEFAULT_WEB_PORT = 2015;
	private static final int DEFAULT_CONTROL_PORT = 2016;
	private static final int DEFAULT_UPLOAD_PORT = 2017;
	private static final int DEFAULT_DOWNLOAD_PORT = 2018;
	private static final int DEFAULT_CLIENT_PORT = 2019;
	private static final int DEFAULT_TIME_OUT = 30000;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	
	public static String getResultPath() {
		return resultPath;
	}

	public static String getLogPath() {
		return logPath;
	}
	
	public static String getTmpPath() {
		return tmpPath;
	}
	
	public static int getWebPort() {
		return webPort;
	}
	
	
	private static String resultPath = DEFAULT_RESULT_PATH;
	private static String logPath = DEFAULT_LOG_PATH;
	private static String tmpPath = DEFAULT_TMP_PATH;
	private static int webPort = DEFAULT_WEB_PORT;
	private static int master_control_port = DEFAULT_CONTROL_PORT;
	private static int master_download_port= DEFAULT_DOWNLOAD_PORT;
	private static int master_upload_port = DEFAULT_UPLOAD_PORT;
	private static int clientPort = DEFAULT_CLIENT_PORT;
	private static int timeOut = DEFAULT_TIME_OUT;
	private static int sleepTime = DEFAULT_SLEEP_TIME;

	public static void show(){
		System.out.println("...........Master Preference...............");
		System.out.println("Result Paths: "+resultPath);
		System.out.println("Log Path: "+logPath);
		System.out.println("Tmp Path: "+tmpPath);
		System.out.println("Web Port: "+webPort);
		System.out.println("Control Port: "+ master_control_port);
		System.out.println("Download Port: "+master_download_port);
		System.out.println("Upload Port: "+master_upload_port);
		System.out.println("Client Port: "+clientPort);
		System.out.println("Time Out:"+timeOut);
		System.out.println();
	}
	
	
	
	public static int getMasterControlPort() {
		return master_control_port;
	}

	public static void setMasterControlPort(int master_control_port) {
		Preference.master_control_port = master_control_port;
	}

	public static int getMasterDownloadPort() {
		return master_download_port;
	}

	public static void setMasterDownloadPort(int master_download_port) {
		Preference.master_download_port = master_download_port;
	}

	public static int getMasterUploadPort() {
		return master_upload_port;
	}

	public static void setMasterUploadPort(int master_upload_port) {
		Preference.master_upload_port = master_upload_port;
	}

	public static void setResultPath(String resultPath) {
		Preference.resultPath = resultPath;
	}

	public static void setLogPath(String logPath) {
		Preference.logPath = logPath;
	}

	public static void setTmpPath(String tmpPath) {
		Preference.tmpPath = tmpPath;
	}

	public static void setWebPort(int webPort) {
		Preference.webPort = webPort;
	}

	public static int getTimeOut() {
		return timeOut;
	}

	public static void setTimeOut(int timeOut) {
		Preference.timeOut = timeOut;
	}

	public static int getClientPort() {
		return clientPort;
	}

	public static void setClientPort(int clientPort) {
		Preference.clientPort = clientPort;
	}
	
	

	public static int getSleepTime() {
		return sleepTime;
	}

	public static void setSleepTime(int sleepTime) {
		Preference.sleepTime = sleepTime;
	}
	
	public static void main(String[] args) {
		show();
	}
}

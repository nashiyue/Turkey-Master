package iie.master.preference;

import java.io.File;

public class Preference {
	private Preference(){}	
	
	private static final String DEFAULT_BASE_PATH = System.getProperty("user.home")+File.separator+"Turkey-Master";
	private static final String DEFAULT_RESULT_PATH = DEFAULT_BASE_PATH+File.separator+"results";
	private static final String DEFAULT_LOG_PATH = DEFAULT_BASE_PATH+File.separator+"logs";
	private static final String DEFAULT_TMP_PATH = DEFAULT_BASE_PATH+File.separator+"tmp";
	private static final int DEFAULT_WEB_PORT = 2015;
	private static final int DEFAULT_SLAVE_PORT = 2016;
	private static final int DEFAULT_CLIENT_PORT = 2017;
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
	
	public static int getSlavePort() {
		return slavePort;
	}
	
	private static String resultPath = DEFAULT_RESULT_PATH;
	private static String logPath = DEFAULT_LOG_PATH;
	private static String tmpPath = DEFAULT_TMP_PATH;
	private static int webPort = DEFAULT_WEB_PORT;
	private static int slavePort = DEFAULT_SLAVE_PORT;
	private static int clientPort = DEFAULT_CLIENT_PORT;
	private static int timeOut = DEFAULT_TIME_OUT;
	private static int sleepTime = DEFAULT_SLEEP_TIME;

	public static void show(){
		System.out.println("...........Master Preference...............");
		System.out.println("Result Paths: "+resultPath);
		System.out.println("Log Path: "+logPath);
		System.out.println("Tmp Path: "+tmpPath);
		System.out.println("Web Port: "+webPort);
		System.out.println("Slave Port: "+slavePort);
		System.out.println("Client Port: "+clientPort);
		System.out.println("Time Out:"+timeOut);
		System.out.println();
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

	public static void setSlavePort(int slavePort) {
		Preference.slavePort = slavePort;
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
	
	public static void main(String[] args) {
		show();
	}

	public static int getSleepTime() {
		return sleepTime;
	}

	public static void setSleepTime(int sleepTime) {
		Preference.sleepTime = sleepTime;
	}
}

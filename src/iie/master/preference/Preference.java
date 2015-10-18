package iie.master.preference;

public class Preference {
	private Preference(){}	
	
	private static final String DEFAULT_RESULT_PATH = "results";
	private static final String DEFAULT_LOG_PATH = "logs";
	private static final String DEFAULT_TMP_PATH = "tmp";
	private static final int DEFAULT_WEB_PORT = 2015;
	private static final int DEFAULT_SLAVE_PORT = 2016;
	private static final int DEFAULT_TIME_OUT = 30000;
	
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
	private static int timeOut = DEFAULT_TIME_OUT;

	public static void show(){
		System.out.println("...........Master Preference...............");
		System.out.println("Result Paths: "+resultPath);
		System.out.println("Log Path: "+logPath);
		System.out.println("Tmp Path: "+tmpPath);
		System.out.println("Web Port: "+webPort);
		System.out.println("Slave Port: "+slavePort);
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
}

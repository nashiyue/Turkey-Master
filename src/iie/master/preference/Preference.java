package iie.master.preference;

public class Preference {
	private Preference(){}	
	
	public static String DEFAULT_RESULT_PATH = "results";
	public static String DEFAULT_LOG_PATH = "logs";
	public static String DEFAULT_TMP_PATH = "tmp";
	public static int DEFAULT_WEB_PORT = 2015;
	public static int DEFAULT_SLAVE_PORT = 2016;
	
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
}

package iie.utils.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class TLogger {
	private TLogger(){}
	
	static{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@SuppressWarnings("rawtypes")
	public static void debug(Class obj,String content){
		Logger.getLogger(obj).debug(content);
	}
	
	@SuppressWarnings("rawtypes")
	public static void error(Class obj,String content){
		Logger.getLogger(obj).error(content);
	}
	
	public static void debug(String obj,String content){
		Logger.getLogger(obj).debug(content);
	}
	
	public static void error(String obj,String content){
		Logger.getLogger(obj).error(content);
	}
}

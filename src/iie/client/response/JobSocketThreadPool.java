package iie.client.response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobSocketThreadPool {
	
	private JobSocketThreadPool() {
	}
	
	private static final int LIMIT = 5;
	
	private static ExecutorService service = Executors.newFixedThreadPool(LIMIT);

	public static void close(){
		service.shutdown();
	}
	
	public static void execute(Runnable runnable){
		service.execute(runnable);
	}
}

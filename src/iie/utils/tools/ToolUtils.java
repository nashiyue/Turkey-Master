package iie.utils.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolUtils {
	private ToolUtils() {
	}
	
	public static String formatDate(long time){
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(System.currentTimeMillis()));
	}
}

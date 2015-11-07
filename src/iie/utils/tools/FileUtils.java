package iie.utils.tools;

import java.io.File;

public class FileUtils {

	public static boolean mkdirWithFileName(String path){
		int flag = path.lastIndexOf(File.separator);
		if(flag != -1){
			mkdirWithoutFileName(path.substring(0,flag));
			return true;
		}
		return false;
	}
	
	public static void mkdirWithoutFileName(String path){
		File file = new File(path);
		file.mkdirs();
	}
	
//	public static void main(String[] args) {
//		String path ="/home/liuwei/logs/tmp";
////		mkdirWithFileName(path);
//		mkdirWithoutFileName(path);
//	}
}

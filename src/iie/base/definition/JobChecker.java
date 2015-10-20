package iie.base.definition;

import java.io.File;

import iie.master.preference.Preference;
import iie.utils.logger.TLogger;

public class JobChecker {

	private JobChecker() {
	}

	private static String jarMissMsg(long id, String jar) {
		return "job:" + id + " jar file [" + jar + "] is missing";
	}

	public static boolean isLegal(Job job) {
		int size = job.getTaskSize();
		if (size < 1) {
			return false;
		} 
		else {
			// 检查jar文件是否都存在
			String base = Preference.getTmpPath() + File.separator
					+ job.getJobId() + File.separator;
			for (int i = 0; i < size; i++) {
				String jar = job.getTask(i).getJarName();
				if (!new File(base + jar).exists()) {
					TLogger.error("iie.base.definition.JobChecker",
							jarMissMsg(job.getJobId(), jar));
					return false;
				}
			}
		}
		return true;
	}
}

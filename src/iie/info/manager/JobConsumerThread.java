package iie.info.manager;

import iie.base.definition.Job;
import iie.base.definition.JobChecker;
import iie.base.definition.Task;
import iie.master.preference.Preference;
import iie.net.netty.ControlMessage;
import iie.net.netty.ControlServerChannelManager;

public class JobConsumerThread extends Thread {

	private boolean isOn;

	public JobConsumerThread() {
		isOn = true;
	}

	@Override
	public void run() {
		while (isOn) {
			if (!JobPriorityQueue.getInstance().isEmpty()) {
				Job job = JobPriorityQueue.getInstance().poll();
				if (JobChecker.isLegal(job)) {
					execute(job);
				}
			}
			try {
				Thread.sleep(Preference.getSleepTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void execute(Job job) {
		for (int i = 0; i < job.getTaskSize(); i++) {
			Task task = job.getTask(i);
			ControlMessage message = new ControlMessage(Preference.MASTER_ID,
					task.getBladeName(), ControlMessage.TYPE_TASK_CONTROL, task.toString());
			ControlServerChannelManager.send(message);
		}
	}

	public void close() {
		isOn = false;
	}

}

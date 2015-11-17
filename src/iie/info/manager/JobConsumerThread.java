package iie.info.manager;

import iie.base.definition.Job;
import iie.base.definition.JobChecker;
import iie.base.definition.Task;
import iie.base.definition.TaskCounterManager;
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
		System.out.println("Server 在监听job队列....");
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
		int count = 0;
		int receivveCount = 0;
		for (int i = 0; i < job.getTaskSize(); i++) {
			Task task = job.getTask(i);
			ControlMessage message = new ControlMessage(job.getJobId()+"",
					task.getBladeName(), ControlMessage.TYPE_TASK_CONTROL, task.toString());
			boolean isSuccess = ControlServerChannelManager.send(message);
			if(!isSuccess){
				System.out.println("JobConsumer send control message failed:"+message);
			}
			else{
				count ++;
				if(task.isUpload()){
					receivveCount++;
				}
			}
		}
		if(receivveCount != 0){
			System.out.println("Job:"+job.getJobId()+" send "+count+"　tasks successful");
			System.out.println("Need upload task number is "+receivveCount);
//			TaskCounterManager.getInstance().sendPut(job.getJobId()+"", count);
			TaskCounterManager.getInstance().sendPut(job.getJobId()+"", receivveCount);
		}
	}

	public void close() {
		isOn = false;
	}

}

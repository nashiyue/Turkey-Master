package iie.info.manager;

import iie.base.definition.Job;
import iie.base.definition.JobChecker;
import iie.master.preference.Preference;

public class JobConsumerThread extends Thread{

	private boolean isOn;
	
	public JobConsumerThread() {
		isOn = true;
	}
	
	@Override
	public void run() {
		while(isOn){
			if(!JobPriorityQueue.getInstance().isEmpty()){
				Job job = JobPriorityQueue.getInstance().poll();
				if(JobChecker.isLegal(job)){
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
	
	private void execute(Job job){
		
	}
	
	public void close(){
		isOn = false;
	}
	
}

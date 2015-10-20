package iie.info.manager;

import iie.base.definition.Job;
import iie.utils.logger.TLogger;

import java.util.Comparator;
import java.util.PriorityQueue;

public class JobPriorityQueue{

	private static JobPriorityQueue pool = null;
	private PriorityQueue<Job> queue = null;
	
	private JobPriorityQueue() {
		queue = new PriorityQueue<Job>(5, new Compara());
	}
	
	public static synchronized JobPriorityQueue getInstance(){
		if(pool == null){
			pool = new JobPriorityQueue();
		}
		return pool;
	}
	
	public synchronized void addJob(Job job){
		queue.add(job);
		TLogger.debug(getClass(), "Job:"+job.getJobId()+" add queue");
	}
	
	public synchronized Job poll(){
		Job job = queue.poll();
		TLogger.debug(getClass(), "Job:"+job.getJobId()+" poll from queue");
		return job;
	}
	
	public synchronized boolean isEmpty(){
		return queue.isEmpty();
	}
	
	class Compara implements Comparator<Job>{

		@Override
		public int compare(Job job1, Job job2) {
			int priority1 = job1.getPriority();
			int priority2 = job2.getPriority();
			if(priority1 == priority2){
				return (int) (job1.getJobId() - job2.getJobId());
			}
			else{
				return priority2 - priority1;
			}
		}
		
	}

}

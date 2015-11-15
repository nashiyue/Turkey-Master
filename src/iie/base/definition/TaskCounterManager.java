package iie.base.definition;

import java.util.HashMap;

public class TaskCounterManager {
	//已经成功发送的消息 JobId counter
	private HashMap<String, Integer> sendCounter = new HashMap<String, Integer>();

	private HashMap<String, Integer> receiveCounter = new HashMap<String, Integer>();
	
	private TaskCounterManager(){}
	
	private static TaskCounterManager instance;
	
	public synchronized static TaskCounterManager getInstance(){
		if(instance == null){
			instance = new TaskCounterManager();
		}
		return instance;
	} 
	
	public synchronized void sendPut(String jobId,int count){
		sendCounter.put(jobId, count);
	}
	
	public synchronized void receivePut(String jobId,int count){
		receiveCounter.put(jobId, count);
	}
	
	public synchronized void send(String jobId){
		if(sendCounter.containsKey(jobId)){
			sendCounter.put(jobId, sendCounter.get(jobId)+1);
		}
		else{
			sendCounter.put(jobId, 1);
		}
	}
	
	public synchronized void receive(String jobId){
		if(receiveCounter.containsKey(jobId)){
			receiveCounter.put(jobId, receiveCounter.get(jobId)+1);
		}
		else{
			receiveCounter.put(jobId, 1);
		}
	}
	
	public synchronized Integer getReceiveCount(String jobId){		
		return receiveCounter.get(jobId);
	}
	
	public synchronized boolean isGetAllResults(String jobId){
		return sendCounter.get(jobId) == receiveCounter.get(jobId);
	}
}

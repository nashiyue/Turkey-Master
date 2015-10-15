package iie.base.definition;

import iie.master.preference.Preference;

import java.util.LinkedList;

public class Job {
	//Job优先级的最大值
	public static final int PRIORITY_MAX = 10;
	
	//Job优先级的最小值
	public static final int PRIORITY_MIN = 1;
	
	//job默认的优先级大小
	public static final int PRIORITY_DEFAULT = 5;
	
	//Master根据系统时间分配的JobID
	private String jobId;
	
	//本次job全局运行的jar文件
	private String runJar;
	
	//Master执行的合并策略
	private String mergePolicy;
	
	//Master最后合并结果的输出路径
	private String resultPath;
	
	//Job的优先级
	private int priority;
	
	//任务列表
	private LinkedList<Task> taskList;
	
	public int getTaskSize(){
		return taskList.size();
	}
	
	public void addTask(Task task){
		taskList.add(task);
	}
	
	public Task getTask(int index){
		if(index < 0 || index >= getTaskSize()){
			return null;
		}
		return taskList.get(index);
	}
	
	public String getJobId() {
		return jobId;
	}

	public String getRunJar() {
		return runJar;
	}

	public void setRunJar(String runJar) {
		this.runJar = runJar;
	}

	public String getMergePolicy() {
		return mergePolicy;
	}

	public void setMergePolicy(String mergePolicy) {
		this.mergePolicy = mergePolicy;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(String priority){
		try {
			setPriority(Integer.valueOf(priority));
		} catch (NumberFormatException e) {
			setPriority(PRIORITY_DEFAULT);
		}
	}
	
	public void setPriority(int priority) {
		if(priority > PRIORITY_MAX || priority < PRIORITY_MIN){
			this.priority = PRIORITY_DEFAULT;
		}
		this.priority = priority;
	}

	public Job() {
		taskList = new LinkedList<Task>();
		priority = PRIORITY_DEFAULT;
		setResultPath(Preference.getResultPath());
		jobId = initJobId();
	}
	
	private String initJobId(){
		String id = "Job";
		return id+System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		String result = "run_jar:"+runJar+" merge_policy:"+mergePolicy+" path:"+resultPath+" priority:"+priority;
		result = jobId+"\n"+result;
		result += "\n";
		for(Task task : taskList){
			result = result + task.toString()+"\n";
		}
		return result;
	}
}

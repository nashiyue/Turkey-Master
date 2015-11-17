package iie.base.definition;

import java.util.LinkedList;

public class Task {
	//执行Task的节点名称或IP
	private String bladeName;
	
	//执行的jar文件
	private String jarName;
	
	//jar文件的启动类
	private String className;
	
	//附加的参数
	private LinkedList<Param> paramList;
	
	//如果允许Task分发到其他节点，则其条件和具体信息
	private Allows allows;
	
	private boolean isUpload;
	
	public static Task getInstance(String toStringContent) throws Exception{
		Task task = new Task();
		String[] contents = toStringContent.split(",");
		for(String line:contents){
			String[] kv = line.split(":",2);
			try {
				switch (kv[0]) {
				case "blade":
					task.setBladeName(kv[1]);
					break;
				case "jar":
					task.setJarName(kv[1]);
					break;
				case "class":
					task.setClassName(kv[1]);
					break;
				case "params":
					if(kv.length==1||kv[1].trim().equals("")){
						//do nothing
					}
					else{
						String[] params = kv[1].split(" ");
						for(String p :params){
							task.addParam(Param.getInstance(p));
						}
					}
					
					break;
				case "allow":
					if(kv[1].trim().equals("no")){
						task.setAllows(new Allows());
					}
					else{
						task.setAllows(Allows.getInstance(kv[1]));						
					}
					break;
				case "isUpload":
					task.setUpload(Boolean.valueOf(kv[1].trim()));
					break;
				default:
					break;
				}
			} catch (Exception e) {
				throw e;
			}
		}
		return task;
	}
	
	public int getParamSize(){
		return paramList.size();
	}
	
	public void addParam(Param param){
		paramList.add(param);
	}
	
	public Param getParam(int index){
		if(index < 0 || index >= getParamSize()){
			return null;
		}
		return paramList.get(index);
	}
	
	private Task(){
		paramList = new LinkedList<Param>();
		allows = new Allows();
	}
	
	public Task(String bladeName){
		this.bladeName = bladeName;
		paramList = new LinkedList<Param>();
		allows = new Allows();
	}

	public String getBladeName() {
		return bladeName;
	}

	public void setBladeName(String bladeName) {
		this.bladeName = bladeName;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Allows getAllows() {
		return allows;
	}

	public void setAllows(Allows allows) {
		if(allows == null){
			this.allows = new Allows();
		}
		this.allows = allows;
	}
	
	@Override
	public String toString() {
		String result =  "blade:"+bladeName+",jar:"+jarName+",class:"+className +",params:";
		for(Param p : paramList){
			result = result + p.key +"="+p.value+" ";
		}
		result += ","+allows.toString();
		result += ",isUpload:"+isUpload;
		return result;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	
//	public static void main(String[] args) throws Exception {
//		Task task = new Task("192.168.11.52");
//		System.out.println(task);
//		System.out.println(Task.getInstance("blade:192.168.11.52,jar:null,class:null,params:,allow:no,isUpload:false"));
//	}
}

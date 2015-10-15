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
	
	public Task(String bladeName){
		this.bladeName = bladeName;
		paramList = new LinkedList<Param>();
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
		this.allows = allows;
	}
	
	@Override
	public String toString() {
		String result =  "blade:"+bladeName+" jar:"+jarName+" class:"+className +" params:";
		for(Param p : paramList){
			result = result + p.key +" "+p.value+" ";
		}
		result += allows.toString();
		return result;
	}
}

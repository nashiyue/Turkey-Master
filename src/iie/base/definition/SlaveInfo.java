package iie.base.definition;

public class SlaveInfo {
	
	private String name;
	private String mem;
	private float load;
	
	private boolean flag;
	
	public SlaveInfo(String name) {
		flag = true;
	}
	
	public void die(){
		flag = false;
	}
	
	public boolean isAlive(){
		return flag;
	}
	
	public String getMem() {
		return mem;
	}
	
	public void setMem(String mem) {
		this.mem = mem;
	}
	
	public float getLoad() {
		return load;
	}
	
	public void setLoad(float load) {
		this.load = load;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		else if(obj == null){
			return false;
		}
		else {
			if(obj instanceof SlaveInfo){
				return this.name.equals(((SlaveInfo)obj).getName());
			}
			else {
				return false;
			}
		}
	}
}

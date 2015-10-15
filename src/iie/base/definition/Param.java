package iie.base.definition;

public class Param{
	
	public String key;
	public String value;
	
	public Param(String key,String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "K="+key+";V="+value;
	}
}

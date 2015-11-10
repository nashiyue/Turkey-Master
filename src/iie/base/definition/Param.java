package iie.base.definition;

public class Param{
	
	public String key;
	public String value;
	
	public static Param getInstance(String content) throws Exception{
		String[] kv = content.split("=");
		Param p = null;
		if(kv.length == 2){			
			if(kv[0].trim().equals("null")){				
				p = new Param(null,kv[1]);
			}
			else{
				p = new Param(kv[0], kv[1]);
			}
		}
		else{
			throw new Exception("Param is error:"+p);
		}
		return p;
	}
	
	public Param(String key,String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key+"="+value;
	}
	
	
}

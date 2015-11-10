package iie.base.definition;

import java.util.LinkedList;

public class Allows {
	public static final String FACTOR_LOAD = "load";
	public static final String FACTOR_MEM = "mem";
	
	public static final String FLAG_YES = "yes";
	public static final String FLAG_PRIORITY = "priority";
	
	private static String sep_flag ="=";
	private static String sep_blade="-";
	
	private boolean isAllow;
	
	private String name;
	private String value;
	private String flag;

	private LinkedList<String> bladeList;
	/**
	 * 这个content是指<allow name="" value="">content</allow>里面的
	 * */
	public void setAllowContent(String content) throws Exception{
		String[] array = content.split(sep_flag);
		if(array.length != 2){
//			for(int i=0; i<array.length; i++){
//				System.out.println("["+array[i]+"]");
//			}
			throw new Exception("Allow content is error:"+content);
		}
		else{
			setFlag(array[0].trim());
			String[] blades = array[1].split(sep_blade);
			for(String blade:blades){
				blade = blade.trim();
				if(!blade.equals("")){					
					addBlade(blade);
				}
			}
		}
	}
	
	public static Allows getInstance(String content) throws Exception{
		String[] array = content.split(" ");
		Allows allows = new Allows();
		String[] arrs = null;
		for(int i =0;i<array.length; i++){
			arrs = array[i].split("=");
			if(arrs.length == 2){
				switch (arrs[0]) {
				case "name":
					allows.setName(arrs[1]);
					break;
				case "value":
					allows.setValue(arrs[1]);
					break;
				case "yes":
					allows.setAllowContent(array[i]);
					break;
				case "priority":
					allows.setAllowContent(array[i]);
					break;
				default:
					break;
				}
			}
			else{
				throw new Exception("Allow format is error:"+array[i]);
			}
		}
		return allows;
	} 
	
	public int getBladeSize(){
		return bladeList.size();
	}
	
	public void addBlade(String blade){
		bladeList.add(blade);
		if(!isAllow){
			isAllow = true;
		}
	}
	
	public String getBlade(int index){
		if(index < 0 || index >= getBladeSize()){
			return null;
		}
		return bladeList.get(index);
	}
	
	public Allows() {
		isAllow = false;
		bladeList = new LinkedList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name.toLowerCase();
		if(name.equals(FACTOR_LOAD) || name.equals(FACTOR_MEM)){
			this.name = name;
		}
		else{
			isAllow = false;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		flag = flag.toLowerCase();
		if(flag.equals(FLAG_YES)){
			this.flag = flag;
		}
		else{
			this.flag = FLAG_PRIORITY;
		}
	}

	public boolean isAllow() {
		return isAllow;
	}
	
	public void allow(){
		isAllow = true;
	}

	@Override
	public String toString() {
		String result = "allow:name="+name+" value="+value+" "+flag+sep_flag;
		if (isAllow) {
			for(String s: bladeList){
				result= result +s+sep_blade;
			}
		}
		else{
			return "allow:no";
		}
		return result.substring(0, result.length()-1);
	}
}

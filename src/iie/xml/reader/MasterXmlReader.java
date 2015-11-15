package iie.xml.reader;

import iie.master.preference.Preference;

import org.dom4j.Element;

public class MasterXmlReader extends XmlReader{

	public static final String MERGE_RESULT_PATH = "MERGE_RESULT_PATH";
	public static final String LOG_PATH = "LOG_PATH";
	public static final String TMP_PATH = "TMP_PATH";
	public static final String WEB_PORT = "WEB_PORT";
	public static final String CLIENT_PORT="CLIENT_PORT";
	public static final String CONTROL_PORT = "CONTROL_PORT";
	public static final String UPLOAD_PORT = "UPLOAD_PORT";
	public static final String DOWNLOAD_PORT = "DOWNLOAD_PORT";
	
	@Override
	protected void handle(Element property) throws Exception {
		if(property.getName().equals("property")){
			String name = property.element("name").getTextTrim();
			String value = property.element("value").getTextTrim();
			if(name.equals(MERGE_RESULT_PATH)){
				Preference.setResultPath(value);
			}
			else if(name.equals(LOG_PATH)){
				Preference.setLogPath(value);
			}
			else if(name.equals(TMP_PATH)){
				Preference.setTmpPath(value);
			}
			else if(name.equals(WEB_PORT)){
				Preference.setWebPort(Integer.valueOf(value));
			}
			else if(name.equals(CLIENT_PORT)){
				Preference.setClientPort(Integer.valueOf(value));
			}
			else if(name.equals(CONTROL_PORT)){
				Preference.setMasterControlPort(Integer.valueOf(value));
			}
			else if(name.equals(DOWNLOAD_PORT)){
				Preference.setMasterDownloadPort(Integer.valueOf(value));
			}
			else if(name.equals(UPLOAD_PORT)){
				Preference.setMasterUploadPort(Integer.valueOf(value));
			}
			else{
				
			}
		}
		else{
			throw new Exception("Xml format is error:"+property);
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		MasterXmlReader reader = new MasterXmlReader();
//		reader.init("conf/conf.xml");
//		Preference.show();
//	}

}

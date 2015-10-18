package iie.xml.reader;

import iie.base.definition.Allows;
import iie.base.definition.Job;
import iie.base.definition.Param;
import iie.base.definition.Task;
import iie.utils.logger.TLogger;

import java.util.Iterator;

import org.dom4j.Element;

public class JobXmlReader extends XmlReader{
	public static final String RUN_JAR = "RUN_JAR";
	public static final String MERGE_POLICY = "MERGE_POLICY";
	public static final String TOTAL_RESULT_PATH = "TOTAL_RESULT_PATH";
	public static final String JOB_PRIORITY = "JOB_PRIORITY";
	
	private Job job;
	
	@Override
	public void init(String path) throws Exception {
		job = new Job();
		super.init(path);
	}
	
	public void recordJobInfo(String jobInfo){
		TLogger.debug(getClass().getName(), job.toString());
	}
	
	public Job getJob() throws Exception{
		String run_jar = job.getRunJar();
		if(run_jar!=null && !run_jar.equals("")){
			for(int i =0; i<job.getTaskSize(); i++){
				String jar = job.getTask(i).getJarName();
				if(jar == null || jar.equals("")){
					job.getTask(i).setJarName(run_jar);
				}
			}
		}
		else{
			for(int i =0; i<job.getTaskSize(); i++){
				String jar = job.getTask(i).getJarName();
				if(jar == null || jar.equals("")){
					throw new Exception();
				}
			}
		}
		recordJobInfo(job.toString());
		return job;
	}
	
	public void showJob(){
		System.out.println(job);
	}
	
	@Override
	protected void handle(Element property) throws Exception {
		if(property.getName().equals("property")){
			String name = property.element("name").getTextTrim();
			if(name.equals(RUN_JAR)){
				job.setRunJar(property.element("method").getTextTrim());
			}
			else if(name.equals(MERGE_POLICY)){
				job.setMergePolicy(property.element("method").getTextTrim());
			}
			else if(name.equals(TOTAL_RESULT_PATH)){
				job.setResultPath(property.element("method").getTextTrim());
			}
			else if(name.equals(JOB_PRIORITY)){
				job.setPriority(property.element("method").getTextTrim());
			}
			else{
				Task task = new Task(name);
				try {
					Element jarElement = property.element("jar");
					if(jarElement!= null){
						task.setJarName(jarElement.getTextTrim());
					}
					task.setClassName(property.element("method").getTextTrim());
					@SuppressWarnings("unchecked")
					Iterator<Element> iterator = property.elementIterator("params");
					while(iterator.hasNext()){
						Element child = iterator.next();
						task.addParam(new Param(child.attributeValue("type", null), child.getTextTrim()));
					}
					Allows allows = new Allows();
					Element allowElement = property.element("allow");
					if(allowElement != null){
						allows.setAllowContent(allowElement.getTextTrim());
						allows.setName(allowElement.attributeValue("name"));
						allows.setValue(allowElement.attributeValue("value"));
					}
					task.setAllows(allows);
					job.addTask(task);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			throw new Exception("Format error:"+property);
		}
	}
	
//	public static void main(String[] args) {
//		JobXmlReader reader = new JobXmlReader();
//		try {
//			reader.init("/home/liuwei/job12.xml");
//			reader.showJob();
//			reader.getJob();
//		} catch (Exception e) {
//			TLogger.error(JobXmlReader.class, e.toString());
//			e.printStackTrace();
//		}		
//	}
}

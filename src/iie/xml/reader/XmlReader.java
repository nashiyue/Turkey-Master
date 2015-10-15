package iie.xml.reader;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class XmlReader {

	public void init(String path) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));
		Element node = document.getRootElement();
		list(node);
	}

	@SuppressWarnings("unchecked")
	private void list(Element root) throws Exception {
		// 得到每一个Property对象，并对其进行处理
		Iterator<Element> iterator = root.elementIterator();
		while (iterator.hasNext()) {
			Element child = iterator.next();
			handle(child);
		}
	}

	protected abstract void handle(Element property) throws Exception;
	
}

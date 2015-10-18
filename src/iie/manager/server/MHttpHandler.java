package iie.manager.server;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MHttpHandler extends AbstractHandler {

	public static final String WEB_APP = "turkey";
	public static final String ERROR_MESSAGE = "URI is error, please check it! ";
	
	private void sendResponse(HttpServletResponse response,String content) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(content);
		response.getWriter().close();
	}
	
	private String getResults(String type,Map<String, String[]> params){
		if(type.equals("index.html")){
			return "Index:"+new Date();
		}
		else if(type.equals("manager.html")){
			return "Manager:"+new Date();
		}
		else{
			return "Turkey:"+new Date();
		}
	}
	
	private String getResponseContent(HttpServletRequest request){
		String uri = request.getRequestURI();
		int index = 0;
		while(uri.charAt(index) == '/'){
			index ++;
		}
		uri = uri.substring(index);
		String[] array = uri.split("/");
		if(array.length != 2){
			return ERROR_MESSAGE+uri;
		}
		else{			
			if(array[0].equals(WEB_APP)){			
				return getResults(array[1], request.getParameterMap());
			}
			else{
				return ERROR_MESSAGE+uri;
			}
		}
	}
	
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		baseRequest.setHandled(true);
		sendResponse(response, getResponseContent(request));
	}

}

package iie.net.netty;

import iie.base.definition.TaskCounterManager;
import iie.master.preference.Preference;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * TODO
 * @author liuwei
 * Time: 2015年11月6日 下午9:31:50
 */
public class UploadServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	
	private String uri = null;
	
	private HttpRequest request = null;
	
	private HttpPostRequestDecoder decoder;
	
	//message、download、upload
	private String type = "message";
	
	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;
    
    static {
//        DiskFileUpload.baseDirectory = Preference.getTmpPath()+File.separator;
        DiskFileUpload.baseDirectory = Preference.getResultPath()+File.separator;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
    	if (msg instanceof HttpRequest) {
    		request = (HttpRequest) msg;
    		
    		uri = sanitizeUri(request.uri());
    		
       		if (request.method() == HttpMethod.POST) {
    			if (decoder != null) {
					decoder.cleanFiles();
					decoder = null;
				}
    			try {
	              decoder = new HttpPostRequestDecoder(factory, request);
	    		} catch (Exception e) {
	        		e.printStackTrace();
	        		writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
	            	ctx.channel().close();
	        		return;
	    		}
    		}
    	}
    	
    	if (decoder != null && msg instanceof HttpContent) {
        	HttpContent chunk = (HttpContent) msg;
        	
        	try {
        		decoder.offer(chunk);
        	} catch (Exception e) {
        		e.printStackTrace();
        		writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
            	ctx.channel().close();
                return;
        	}
        	
        	readHttpDataChunkByChunk();
        	
        	if (chunk instanceof LastHttpContent) {
        		writeResponse(ctx.channel(), HttpResponseStatus.OK, "");
                reset();
                return;
            }
        }
    }

	private String sanitizeUri(String uri) {
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, "ISO-8859-1");
			} catch(UnsupportedEncodingException e1) {
				throw new Error();
			}
		}

		return uri;
	}

    private void reset() {
        request = null;

        //销毁decoder释放所有的资源
        decoder.destroy();
        
        decoder = null;
    }

    /**
     * 通过chunk读取request，获取chunk数据
     * @throws IOException 
     */
    private void readHttpDataChunkByChunk() throws IOException {
    	try {
	    	while (decoder.hasNext()) {
	        	
	            InterfaceHttpData data = decoder.next();
	            if (data != null) {
	                try {
	                    writeHttpData(data);
	                } finally {
	                    data.release();
	                }
	            }
	        }
    	} catch (EndOfDataDecoderException e1) {
    		System.out.println("end chunk");
    	}
    }

    private void writeHttpData(InterfaceHttpData data) throws IOException {
        if (data.getHttpDataType() == HttpDataType.FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            if (fileUpload.isCompleted()) {
            	String jobId = uri.substring("result_".length());
            	TaskCounterManager.getInstance().receive(jobId);
            	int count = TaskCounterManager.getInstance().getReceiveCount(jobId);
            	
				StringBuffer fileNameBuf = new StringBuffer();
				File dir = new File(DiskFileUpload.baseDirectory+jobId);
				if(!dir.exists()){
					dir.mkdirs();
				}
				fileNameBuf.append(DiskFileUpload.baseDirectory+jobId+File.separator)
		                   .append(uri).append("_"+count);
				System.out.println("fileNamebuf:"+fileNameBuf.toString()+"......");
				fileUpload.renameTo(new File(fileNameBuf.toString()));
            }
        } else if (data.getHttpDataType() == HttpDataType.Attribute) {
        	Attribute attribute = (Attribute) data;
//        	if(CommonParam.DOWNLOAD_COLLECTION.equals(attribute.getName())) {
//        		SynchMessageWatcher.newBuild().getMsgQueue().add(attribute.getValue());
//        	}
        	System.out.println(attribute.getName()+"......"+attribute.getValue());
        }
    }
    
//    private void writeDownLoadResponse(ChannelHandlerContext ctx, RandomAccessFile raf, File file) throws Exception {
//    	long fileLength = raf.length();
//    	
//    	//判断是否关闭请求响应连接
//        boolean close = HttpHeaderValues.CLOSE.equalsIgnoreCase(request.headers().get(HttpHeaderNames.CONNECTION))
//                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
//                && !HttpHeaderValues.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(HttpHeaderNames.CONNECTION));
//        
//        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
////        HttpHeaders.(response, fileLength);
//        response.headers().setLong(HttpHeaderNames.CONTENT_LENGTH, fileLength);
//        setContentHeader(response, file);
//        
//        if (!close) {
//            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        }
//		
//        ctx.write(response);
//        System.out.println("读取大小："+fileLength);
//        
//        final FileRegion region = new DefaultFileRegion(raf.getChannel(), 0, fileLength);
//		ChannelFuture writeFuture = ctx.write(region, ctx.newProgressivePromise());
//		writeFuture.addListener(new ChannelProgressiveFutureListener() {
//            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
//                if (total < 0) {
//                    System.err.println(future.channel() + " Transfer progress: " + progress);
//                } else {
//                    System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
//                }
//            }
//
//            public void operationComplete(ChannelProgressiveFuture future) {
//            }
//        });
//		
//	    ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//        if(close) {
//        	raf.close();
//        	lastContentFuture.addListener(ChannelFutureListener.CLOSE);
//        }
//	}
    
//    private static void setContentHeader(HttpResponse response, File file) {
//        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
//        
//        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
//        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
//
//        // Date header
//        Calendar time = new GregorianCalendar();
//        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));
//
//        // Add cache headers
//        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
//        response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
//        response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
//        response.headers().set(HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(file.lastModified())));
//    }
    
    private void writeResponse(Channel channel, HttpResponseStatus httpResponseStatus, String returnMsg) {
    	String resultStr = "节点【localhost】";
    	if(httpResponseStatus.code() == HttpResponseStatus.OK.code()) {
    		resultStr += "正常接收";
    		if("message".equals(type)) {
    			resultStr += "字符串。";
    		} else if("upload".equals(type)) {
    			resultStr += "上传文件。";
    		} else if("download".equals(type)) {
    			resultStr += "下载文件名。";
    		}
    	} else if(httpResponseStatus.code() == HttpResponseStatus.INTERNAL_SERVER_ERROR.code()) {
    		resultStr += "接收";
    		if("message".equals(type)) {
    			resultStr += "字符串";
    		} else if("upload".equals(type)) {
    			resultStr += "上传文件";
    		} else if("download".equals(type)) {
    			resultStr += "下载文件名";
    		}
    		resultStr += "的过程中出现异常："+returnMsg;
    	}
        //将请求响应的内容转换成ChannelBuffer.
        ByteBuf buf = Unpooled.copiedBuffer(resultStr, CharsetUtil.UTF_8);

        //判断是否关闭请求响应连接
        boolean close = HttpHeaderValues.CLOSE.equalsIgnoreCase(request.headers().get(HttpHeaderNames.CONNECTION))
                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
                && !HttpHeaderValues.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(HttpHeaderNames.CONNECTION));
        
        //构建请求响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, httpResponseStatus, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (!close) {
            //若该请求响应是最后的响应，则在响应头中没有必要添加'Content-Length'
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
        }
        
        //发送请求响应
        ChannelFuture future = channel.writeAndFlush(response);
        //发送请求响应操作结束后关闭连接
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	cause.getCause().printStackTrace();
    	writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, "数据文件通过过程中出现异常："+cause.getMessage().toString());
        ctx.channel().close();
    }
}
package com.ikwang.fungi.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ikwang.fungi.IRequestHandler;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class HandlerFactory  extends HandlerBase {

	private Map<String,IRequestHandler> handlers=new HashMap<String,IRequestHandler>();

	public HandlerFactory(Map<String,IRequestHandler> map) {
		for (Entry<String, IRequestHandler> entry : map.entrySet()) {
			String[] methods=entry.getKey().split(",");
			IRequestHandler handler=entry.getValue();
			handler.setParent(this);
			for (String m:methods){
				handlers.put(m, handler);
			}
		}
	}
	
	@Override
	public Response handle(IRequest request, Response response) {
		 
		if(handlers.containsKey(request.getMethod())){
			return handlers.get(request.getMethod()).handle(request, response);
		}
		response.setStatus(Response.STATUS_FAILED_METHODNOTSUPPORTED);
		response.setMessage("method not supported");
		return response;
	}

 
}

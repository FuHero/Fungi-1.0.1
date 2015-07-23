package com.ikwang.fungi;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public interface IRequestHandler {
	
	/**
	 * handles the given request
	 * @param request the request
	 * @param response the passing through response
	 * @return the response
	 * */
	Response handle(IRequest request,Response response);
	
	/**
	 * sets the parent of this handler
	 * @param parent the parent
	 * */
	void setParent(IRequestHandler parent);
}

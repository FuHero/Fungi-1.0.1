package com.ikwang.fungi.handler;

import java.util.List;

import com.ikwang.fungi.IRequestHandler;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class HandlerChain extends HandlerBase {

	
	private List<IRequestHandler> chain;
	
	public HandlerChain(List<IRequestHandler> chain) {
		this.chain=chain;
		for (IRequestHandler iRequestHandler : chain) {
			iRequestHandler.setParent(this);
		}
	}

	@Override
	public Response handle(IRequest request, Response response) {
		Response ret=response;
		for (IRequestHandler iRequestHandler : chain) {
			ret=iRequestHandler.handle(request, ret);
		}
		return ret;
	}
}

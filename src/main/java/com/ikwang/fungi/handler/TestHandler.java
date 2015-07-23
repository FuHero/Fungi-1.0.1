package com.ikwang.fungi.handler;

import com.ikwang.fungi.IRequestHandler;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class TestHandler implements IRequestHandler {

	@Override
	public Response handle(IRequest request, Response response) {
		response.setValue("hello");
		return response;
	}

	@Override
	public void setParent(IRequestHandler parent) {
		// TODO Auto-generated method stub
		
	}

}

package com.ikwang.fungi;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public interface IResponseSerializer {
	/**
	 * serializes the Response object to String
	 * @param response the response
	 * @param request the request
	 * @return the serialized string,null if this serializer doens't support the format
	 * 
	 * */
	String serialize(Response response,IRequest request,String format);
}

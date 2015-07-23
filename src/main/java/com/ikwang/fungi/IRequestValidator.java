package com.ikwang.fungi;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public interface IRequestValidator {
	/**
	 * validates the given request instance
	 * @param request the request
	 * @return the response,set the status to Resonse.STATUS_OK if the request passed in is a valid request,otherwise,the error code
	 * 
	 * */
	Response validate(IRequest request);
}

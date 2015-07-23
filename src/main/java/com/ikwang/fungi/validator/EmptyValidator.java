package com.ikwang.fungi.validator;

import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class EmptyValidator implements IRequestValidator {

	@Override
	public Response validate(IRequest request) {
		
		return new Response(Response.STATUS_OK);
	}

}

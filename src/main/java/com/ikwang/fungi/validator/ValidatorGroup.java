package com.ikwang.fungi.validator;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class ValidatorGroup implements IRequestValidator {

	private List<IRequestValidator> list;
	
	public ValidatorGroup(List<IRequestValidator> validators){
		list=validators;
		Assert.notNull(validators, "validators can not be null");
	}
	
	@Override
	public Response validate(IRequest request) {
		Response ret=new Response(Response.STATUS_OK);
		Iterator<IRequestValidator> iterator= list.iterator();
		while(iterator.hasNext()){
			ret=iterator.next().validate(request);
			if(ret.ok() == false){
				break;
			}
		}
		return ret;
	}

}

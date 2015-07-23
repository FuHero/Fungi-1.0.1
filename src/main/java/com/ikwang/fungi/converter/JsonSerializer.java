package com.ikwang.fungi.converter;

import java.io.IOException;

import com.ikwang.fungi.IResponseSerializer;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.util.JsonUtil;

public class JsonSerializer implements IResponseSerializer {

	private static final String JSON="json";
	
	@Override
	public String serialize(Response response, IRequest request,String format) {
		if(JSON.equals(format) == false)
			return null;
		 try {
			return JsonUtil.toJson(response);
		} catch (IOException e) {
			
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		 
	}

}

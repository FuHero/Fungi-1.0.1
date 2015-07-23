package com.ikwang.fungi.converter;

import java.util.ArrayList;
import java.util.List;

import com.ikwang.fungi.IResponseSerializer;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class SerializerGroup implements IResponseSerializer {

	private List<IResponseSerializer> list;
	public SerializerGroup(List<IResponseSerializer> converters) {
		this.list=converters;
		if(list == null){
			list=new ArrayList<IResponseSerializer>();
		}
	}

	@Override
	public String serialize(Response response, IRequest request,String format) {
		String ret=null;
		for (IResponseSerializer iResponseConverter : list) {
			ret=iResponseConverter.serialize(response, request,format);
			if(ret != null){//if converted
				break;
			}
			
		}
		
		return ret;
	}

}

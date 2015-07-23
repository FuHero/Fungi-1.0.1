package com.ikwang.fungi;

import org.springframework.web.context.request.WebRequest;

import com.ikwang.fungi.model.IRequest;

public interface IRequestParser {
	/**
	 * parses the given org.springframework.web.context.request.WebRequest into com.ikwang.fungi.model.Request
	 * @param request the WebRequest instance
	 * @return an instance of com.ikwang.fungi.model.Request
	 * 
	 * */
	public IRequest parse(WebRequest request);
}

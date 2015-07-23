package com.ikwang.fungi;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public interface IExceptionInterpreter {
	String interpret(Exception exception,IRequest request,Response response);
}

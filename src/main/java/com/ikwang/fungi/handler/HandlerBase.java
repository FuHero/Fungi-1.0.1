package com.ikwang.fungi.handler;

import com.ikwang.fungi.IRequestHandler;

public abstract class HandlerBase implements IRequestHandler {

 
	protected IRequestHandler parent;
	@Override
	public void setParent(IRequestHandler parent) {
		 this.parent=parent;
	}

}

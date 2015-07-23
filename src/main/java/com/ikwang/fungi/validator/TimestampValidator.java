package com.ikwang.fungi.validator;

import java.util.Date;

import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.util.DateUtil;

public class TimestampValidator implements IRequestValidator {

	private int maximumPermissibleError=10*60;//in seconds
	public TimestampValidator() {
		
	}

	@Override
	public Response validate(IRequest request) {
		Date time=request.getDate("timestamp");
		if(time == null){
			return Response.fail(Response.STATUS_FAILED_PARAMETERCHECK, "parameter timestamp is required");
		}
		Date now=new Date();
		boolean valid=errorCheck(time,now);
		
		if(valid == false){
			return Response.fail(Response.STATUS_FAILED_TIMESTAMPCHECK,
					String.format("parameter timestamp is invalid,the maximum permissible deviation allowed is %s seconds,current server time:%s"
					,maximumPermissibleError,DateUtil.toString(now)));
			
		}
		return Response.ok(null);
	}

	private boolean errorCheck(Date time,Date now) {
		int span=Math.abs((int)(time.getTime()-now.getTime())/1000);
		return span<=maximumPermissibleError;
	}

	public int getMaximumPermissibleError() {
		return maximumPermissibleError;
	}

	public void setMaximumPermissibleError(int maximumPermissibleError) {
		this.maximumPermissibleError = maximumPermissibleError;
	}

}

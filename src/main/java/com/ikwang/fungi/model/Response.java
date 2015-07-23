// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Response.java

package com.ikwang.fungi.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonWriteNullProperties;

@SuppressWarnings("deprecation")
public class Response implements Serializable
{
    public String toString()
    {
        return (new StringBuilder("Response [status=")).append(status).append(", message=").append(message).append(", value=").append(value).append("]").toString();
    }

    public static Response ok(Object value)
    {
        return new Response(STATUS_OK, value);
    }

    public static Response fail(String status, String message)
    {
        Response ret = new Response(status);
        ret.setMessage(message);
        return ret;
    }

    public Response(String status)
    {
        this.status = status;
    }

    public Response(String status, Object value)
    {
        this.status = status;
        setValue(value);
    }

    public Response(Response response)
    {
        status = response.status;
        setValue(response.getValue());
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean ok()
    {
        return STATUS_OK.equals(status);
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static String STATUS_OK = "200";
    public static String STATUS_FAILED_PARAMETERCHECK = "400";
    public static String STATUS_FAILED_TIMESTAMPCHECK = "402";
    public static String STATUS_FAILED_SIGNATURECHECK = "403";
    public static String STATUS_FAILED_METHODNOTSUPPORTED = "404";
    public static String STATUS_FAILED_METHODDEPRECATED = "405";
    public static String STATUS_FAILED_RESPONSEFORMATNOTSUPPORTED = "406";
    public static String STATUS_FAILED_AUTHENCATION = "401";
    public static String STATUS_FAILED_ACCOUNT_ALREADY_EXIST = "512";
    public static String STATUS_FAILED_ACCOUNT_NAME_NOT_MATCH = "513";
    public static String STATUS_FAILED_INVALID_STATE = "514";
    public static String STATUS_FAILED_SERVER = "500";
    public static String STATUS_FAILED_ACCOUNT_NOT_EXIST = "510";
    public static String STATUS_FAILED_INSUFFICIENT_BALANCE = "511";
    public static String STATUS_FAILED_OPERATIONUNSUPPORTED = "515";
    public static String STATUS_FAILED_INVOKETOOFAST = "420";
    public static String STATUS_FAILED_PAYMENTMETHODNOTSUPPORT="516";
    private static final long serialVersionUID = 0xde4c1cbab39dbf26L;
	private String status;

	@JsonWriteNullProperties(false)
	private String message;
	@JsonProperty(value="return")
	@JsonWriteNullProperties(false)
	private Object value;

}

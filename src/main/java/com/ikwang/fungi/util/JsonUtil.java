// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JsonUtil.java

package com.ikwang.fungi.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public final class JsonUtil
{

    private JsonUtil()
    {
    }

    @SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> clazz)
        throws IOException
    {
        return (T) (clazz.equals(String.class) ? json : MAPPER.readValue(json, clazz));
    }

    @SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, TypeReference<T> typeReference)
        throws IOException
    {
        return (T) (typeReference.getType().equals(String.class) ? json : MAPPER.readValue(json, typeReference));
    }

    public static String toJson(Object src)
        throws IOException
    {
        return (src instanceof String) ? (String)src : MAPPER.writeValueAsString(src);
    }

    public static String toJson(Object src, org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion)
        throws IOException
    {
        if(src instanceof String)
        {
            return (String)src;
        } else
        {
            ObjectMapper customMapper = generateMapper(inclusion);
            return customMapper.writeValueAsString(src);
        }
    }

    public static String toJson(Object src, ObjectMapper mapper)
        throws IOException
    {
        if(mapper != null)
        {
            if(src instanceof String)
                return (String)src;
            else
                return mapper.writeValueAsString(src);
        } else
        {
            return null;
        }
    }

    public static ObjectMapper mapper()
    {
        return MAPPER;
    }

    private static ObjectMapper generateMapper(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion inclusion)
    {
        ObjectMapper customMapper = new ObjectMapper();
        customMapper.setSerializationInclusion(inclusion);
        customMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        customMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return customMapper;
    }

    private static ObjectMapper MAPPER;

    static 
    {
        MAPPER = generateMapper(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.ALWAYS);
    }
}

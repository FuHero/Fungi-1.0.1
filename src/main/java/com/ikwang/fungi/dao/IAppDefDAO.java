package com.ikwang.fungi.dao;

import org.springframework.stereotype.Repository;

import com.ikwang.fungi.model.AppDef;
@Repository

public interface IAppDefDAO {
	int create(AppDef appdef);
	AppDef getById(String id);
	
}

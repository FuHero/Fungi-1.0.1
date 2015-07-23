package com.ikwang.fungi.dao;

import org.springframework.stereotype.Repository;

import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.UpdateParam;
@Repository

public interface IIKwangEventDAO {
	public abstract int create(IKwangEvent event);
	public abstract IKwangEvent getById(String id);
	public abstract int update(UpdateParam param);
}

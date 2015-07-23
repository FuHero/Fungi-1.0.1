package com.ikwang.fungi.dao;

import java.util.List;

import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.search.SearchBase;

public interface IBaseDAO<T> {

	public abstract int create(T obj);

	public abstract  T getById(String id);

	public abstract List<T> search(SearchBase bs);

	public abstract int update(UpdateParam param);

}
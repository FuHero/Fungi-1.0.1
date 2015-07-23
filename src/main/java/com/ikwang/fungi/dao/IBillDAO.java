package com.ikwang.fungi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.search.SearchBase;
@Repository

public interface IBillDAO { 
	int create(Bill bill);
	Bill getById(String id);
	List<Bill> search(SearchBase bs);
	int update(UpdateParam param);
}

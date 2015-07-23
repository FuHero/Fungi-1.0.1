package com.ikwang.fungi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.search.SearchBase;
@Repository

public interface ITransactionDAO {
	int create(Transaction transaction);
	Transaction getById(Long id);
	List<Transaction> search(SearchBase bs);
	int update(UpdateParam param);
}

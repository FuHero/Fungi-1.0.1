package com.ikwang.fungi.dao;

import org.springframework.stereotype.Repository;

import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.UpdateParam;
@Repository

public interface IAccountDAO {
	int create(Account account);
	Account getById(String id);
	int update(UpdateParam param);
}

package com.project.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.accounts.dao.AccountDao;
import com.project.accounts.service.AccountService;

public class AccountServiceImpl extends AccountService {

	private AccountDao accountDao;

	@Autowired
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
}

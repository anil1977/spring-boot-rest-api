package com.project.accounts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.project.accounts.dao.AccountDao;
import com.project.accounts.domain.Account;

/**
 * DAO/persistence layer is not yet implemented, this is a dummy implementation
 * 
 * @author Anil
 */

@Repository
public class AccountDaoImpl implements AccountDao {

	private static Map<Long, Account> accounts = new HashMap<Long, Account>();

	@Override
	public List<Account> getAllAccounts() {
		return accounts.values().stream().collect(Collectors.toList());
	}

	@Override
	public Account addNewAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

}

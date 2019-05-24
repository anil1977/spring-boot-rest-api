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
	private static Long seq = 0L;

	@Override
	public List<Account> getAllAccounts() {
		return accounts.values().stream().collect(Collectors.toList());
	}

	@Override
	public Account addNewAccount(Account account) {
		if (!validAccount(account)) {
			return null;
		}

		account.setId(getNextSeq());
		accounts.put(account.getId(), account);
		return account;
	}

	@Override
	public boolean deleteAccount(long id) {
		return accounts.remove(id) != null;
	}

	// simple null check for now
	private boolean validAccount(Account account) {

		return account.getFirstName() != null && !account.getFirstName().trim().isEmpty()
				&& account.getSecondName() != null && !account.getSecondName().trim().isEmpty()
				&& account.getAccountNumber() != null && !account.getAccountNumber().trim().isEmpty();
	}

	private Long getNextSeq() {
		return ++seq;
	}

}

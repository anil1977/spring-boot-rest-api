package com.project.accounts.dao;

import java.util.List;

import com.project.accounts.domain.Account;

public interface AccountDao {

	List<Account> getAllAccounts();

	Account addNewAccount(Account account);

}

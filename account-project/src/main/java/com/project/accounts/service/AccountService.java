package com.project.accounts.service;

import java.util.List;

import com.project.accounts.domain.Account;

public interface AccountService {

	List<Account> getAllAccounts();

	Account addNewAccount(Account account);

	boolean deleteAccount(long l);

}

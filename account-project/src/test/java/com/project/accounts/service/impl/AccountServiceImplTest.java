package com.project.accounts.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.project.accounts.dao.AccountDao;
import com.project.accounts.domain.Account;
import com.project.accounts.service.AccountService;

public class AccountServiceImplTest {

	@Mock
	private AccountDao accountDao;

	@InjectMocks
	private AccountService accountService;

	@Before
	public void setUp() throws Exception {
		accountService = new AccountServiceImpl(accountDao);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllAccounts() {
		final List<Account> mockAccounts = new ArrayList<Account>();
		final Account mockAccount1 = getFirstSampleAccount();
		final Account mockAccount2 = getSecondSampleAccount();
		mockAccounts.add(mockAccount1);
		mockAccounts.add(mockAccount2);

		when(accountDao.getAllAccounts()).thenReturn(mockAccounts);
		assertEquals(mockAccounts, accountService.getAllAccounts());
		verify(accountDao, times(1)).getAllAccounts();
	}

	@Test
	public void testGetAllAccounts_when_no_accounts_in_db() {
		final List<Account> mockAccounts = new ArrayList<Account>();

		when(accountDao.getAllAccounts()).thenReturn(mockAccounts);
		assertEquals(mockAccounts, accountService.getAllAccounts());
		verify(accountDao, times(1)).getAllAccounts();
	}

	@Test
	public void testAddNewAccount() {
		Account accountToAdd = getFirstSampleAccount();
		Account mockAccount = getFirstSampleAccount();
		mockAccount.setId(1L);

		when(accountDao.addNewAccount(Mockito.any(Account.class))).thenReturn(mockAccount);
		assertEquals(mockAccount, accountService.addNewAccount(accountToAdd));
		verify(accountDao, times(1)).addNewAccount(accountToAdd);

	}

	@Test
	public void testAddNewAccount_validation_failure() {
		Account accountToAdd = getInvalidSampleAccount();
		accountToAdd.setId(1L);

		when(accountDao.addNewAccount(Mockito.any(Account.class))).thenReturn(null);
		assertNull(accountService.addNewAccount(accountToAdd));
		verify(accountDao, times(1)).addNewAccount(accountToAdd);

	}

	@Test
	public void testDeleteAccount_success() {
		long accounIdForDeletion = 101L;
		when(accountDao.deleteAccount(Mockito.anyLong())).thenReturn(true);
		assertTrue(accountService.deleteAccount(accounIdForDeletion));
		verify(accountDao, times(1)).deleteAccount(accounIdForDeletion);

	}

	@Test
	public void testDeleteAccount_failure() {
		long accounIdForDeletion = 101L;
		when(accountDao.deleteAccount(Mockito.anyLong())).thenReturn(false);
		assertFalse(accountService.deleteAccount(accounIdForDeletion));
		verify(accountDao, times(1)).deleteAccount(accounIdForDeletion);
	}

	private Account getSecondSampleAccount() {
		return new Account("Bob", "Taylor", "222");
	}

	private Account getFirstSampleAccount() {
		return new Account("Alex", "Smith", "111");
	}

	private Account getInvalidSampleAccount() {
		return new Account("Carol", "Miller", "");
	}
}

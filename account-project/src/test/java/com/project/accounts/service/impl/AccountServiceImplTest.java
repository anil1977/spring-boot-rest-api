package com.project.accounts.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.accounts.dao.AccountDao;
import com.project.accounts.domain.Account;
import com.project.accounts.service.AccountService;

public class AccountServiceImplTest {

	@Mock
	AccountDao accountDao;

	@InjectMocks
	AccountService accountService;

	@Before
	public void setUp() throws Exception {
		accountService = new AccountServiceImpl(accountDao);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllAccounts() {
		List<Account> mockAccounts = new ArrayList<Account>();
		Account mockAccount1 = new Account("Alex", "Smith", "111");
		Account mockAccount2 = new Account("Bob", "Taylor", "222");
		mockAccounts.add(mockAccount1);
		mockAccounts.add(mockAccount2);

		when(accountDao.getAllAccounts()).thenReturn(mockAccounts);
		assertEquals(mockAccounts, accountService.getAllAccounts());
		verify(accountDao, times(1)).getAllAccounts();
	}
}

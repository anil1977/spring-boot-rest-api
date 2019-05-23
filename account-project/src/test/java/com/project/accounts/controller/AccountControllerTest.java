package com.project.accounts.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.project.accounts.controller.AccountController;
import com.project.accounts.domain.Account;
import com.project.accounts.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, secure = false)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@Test
	public void getAllAccounts() throws Exception {
		List<Account> mockAccounts = new ArrayList<Account>();
		Account mockAccount1 = new Account("Alex", "Smith", "111");
		Account mockAccount2 = new Account("Bob", "Taylor", "222");
		mockAccounts.add(mockAccount1);
		mockAccounts.add(mockAccount2);

		Mockito.when(accountService.getAllAccounts()).thenReturn(mockAccounts);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account-project/rest/account/json")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}

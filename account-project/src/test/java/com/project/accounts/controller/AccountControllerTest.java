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
		final List<Account> mockAccounts = new ArrayList<Account>();
		final Account mockAccount1 = getFirstSampleAccount();
		final Account mockAccount2 = getSecondSampleAccount();
		mockAccounts.add(mockAccount1);
		mockAccounts.add(mockAccount2);

		Mockito.when(accountService.getAllAccounts()).thenReturn(mockAccounts);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account-project/rest/account/json")
				.accept(MediaType.APPLICATION_JSON);

		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		final MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void createAccount_success() throws Exception {
		final Account mockAccount = getFirstSampleAccount();
		mockAccount.setId(1L);

		Mockito.when(accountService.addNewAccount(Mockito.any(Account.class))).thenReturn(mockAccount);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account-project/rest/account/json")
				.accept(MediaType.APPLICATION_JSON).content(getSampleAccounJson())
				.contentType(MediaType.APPLICATION_JSON);

		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		final MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void createAccount_validation_failure() throws Exception {
		final Account mockAccount = getInvalidSampleAccount();
		mockAccount.setId(1L);

		Mockito.when(accountService.addNewAccount(mockAccount)).thenReturn(null);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account-project/rest/account/json")
				.accept(MediaType.APPLICATION_JSON).content(getSampleAccounJson())
				.contentType(MediaType.APPLICATION_JSON);

		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		final MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void deleteAccount_success() throws Exception {

		Mockito.when(accountService.deleteAccount(Mockito.anyLong())).thenReturn(true);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/account-project/rest/account/json/1")
				.accept(MediaType.APPLICATION_JSON);

		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		final MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteAccount_failure() throws Exception {

		Mockito.when(accountService.deleteAccount(Mockito.anyLong())).thenReturn(false);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/account-project/rest/account/json/1")
				.accept(MediaType.APPLICATION_JSON);

		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		final MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
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

	private String getSampleAccounJson() {
		return "{\"firstName\":\"Alex\",\"secondName\":\"Smith\",\"accountNumber\":\"111\"}";
	}
}

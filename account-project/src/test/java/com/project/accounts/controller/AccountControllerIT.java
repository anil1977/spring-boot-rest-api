package com.project.accounts.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.project.accounts.AccountApplication;
import com.project.accounts.dao.AccountDao;
import com.project.accounts.domain.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private AccountDao accountDao;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private HttpHeaders headers = new HttpHeaders();

	@Before
	public void before() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		ReflectionTestUtils.setField(accountDao, "seq", 0L);
		ReflectionTestUtils.setField(accountDao, "accounts", new HashMap<Long, Account>());
	}

	@Test
	public void testGetAllAccounts_single() {

		final Account account = getFirstSampleAccount();
		final HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
		final ResponseEntity<String> createResponse = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity, String.class);

		assertEquals(createResponse.getStatusCode(), HttpStatus.OK);
		assertEquals(createResponse.getBody(), "{\"message\":\"account has been successfully added\"}");

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.GET, HttpEntity.EMPTY,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("[{\"id\":1,\"firstName\":\"Alex\",\"secondName\":\"Smith\",\"accountNumber\":\"111\"}]",
				response.getBody());
	}

	@Test
	public void testGetAllAccounts_multiple() {

		final Account account1 = getFirstSampleAccount();
		final HttpEntity<Account> entity1 = new HttpEntity<Account>(account1, headers);
		final ResponseEntity<String> createResponse1 = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity1, String.class);
		assertEquals(createResponse1.getStatusCode(), HttpStatus.OK);
		assertEquals(createResponse1.getBody(), "{\"message\":\"account has been successfully added\"}");

		final Account account2 = getSecondSampleAccount();
		final HttpEntity<Account> entity2 = new HttpEntity<Account>(account2, headers);
		final ResponseEntity<String> createResponse2 = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity2, String.class);
		assertEquals(createResponse2.getStatusCode(), HttpStatus.OK);
		assertEquals(createResponse2.getBody(), "{\"message\":\"account has been successfully added\"}");

		final Account account3 = getThirdSampleAccount();
		final HttpEntity<Account> entity3 = new HttpEntity<Account>(account3, headers);
		final ResponseEntity<String> createResponse3 = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity3, String.class);
		assertEquals(createResponse3.getStatusCode(), HttpStatus.OK);
		assertEquals(createResponse3.getBody(), "{\"message\":\"account has been successfully added\"}");

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.GET, HttpEntity.EMPTY,
				String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(
				"[{\"id\":1,\"firstName\":\"Alex\",\"secondName\":\"Smith\",\"accountNumber\":\"111\"},{\"id\":2,\"firstName\":\"Bob\",\"secondName\":\"Taylor\",\"accountNumber\":\"222\"},{\"id\":3,\"firstName\":\"Carol\",\"secondName\":\"Davis\",\"accountNumber\":\"333\"}]",
				response.getBody());
	}

	@Test
	public void testAddNewAccount() {

		final Account account = getFirstSampleAccount();
		final HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"message\":\"account has been successfully added\"}", response.getBody());
	}

	@Test
	public void testDeleteAccount_accountNotFound() {

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json/999"), HttpMethod.DELETE, HttpEntity.EMPTY,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"message\":\"account not found\"}", response.getBody());
	}

	@Test
	public void testDeleteAccount() {

		final Account account = getFirstSampleAccount();
		final HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
		final ResponseEntity<String> createResponse = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json"), HttpMethod.POST, entity, String.class);
		assertEquals(createResponse.getStatusCode(), HttpStatus.OK);
		assertEquals(createResponse.getBody(), "{\"message\":\"account has been successfully added\"}");

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/account-project/rest/account/json/1"), HttpMethod.DELETE, HttpEntity.EMPTY,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"message\":\"account successfully deleted\"}", response.getBody());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private Account getFirstSampleAccount() {
		return new Account("Alex", "Smith", "111");
	}

	private Account getThirdSampleAccount() {
		return new Account("Carol", "Davis", "333");
	}

	private Account getSecondSampleAccount() {
		return new Account("Bob", "Taylor", "222");
	}
}

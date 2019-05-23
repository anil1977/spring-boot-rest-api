package com.project.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.accounts.domain.Account;
import com.project.accounts.domain.ResponseMessage;
import com.project.accounts.service.AccountService;

@RestController
public class AccountController {

	private static final String ACCOUNT_CREATION_SUCCESSFUL = "account has been successfully added";
	private static final String ACCOUNT_CREATION_FAILURE = "account information provided is incomplete to create a new account";
	private static final String ACCOUNT_DELETION_SUCCESSFUL = "account successfully deleted";
	private static final String ACCOUNT_DELETION_FAILURE = "account not found";

	private AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/account-project/rest/account/json")
	@ResponseBody
	public ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.ok(accountService.getAllAccounts());
	}

	@PostMapping("/account-project/rest/account/json")
	@ResponseBody
	public ResponseEntity<ResponseMessage> addNewAccount(@RequestBody Account account) {
		final Account newAccount = accountService.addNewAccount(account);
		if (newAccount == null) {
			return ResponseEntity.badRequest().body(new ResponseMessage(ACCOUNT_CREATION_FAILURE));
		}

		return ResponseEntity.ok(new ResponseMessage(ACCOUNT_CREATION_SUCCESSFUL));
	}

	@DeleteMapping("/account-project/rest/account/json/{id}")
	@ResponseBody
	public ResponseEntity<ResponseMessage> deleteAccount(@PathVariable Long id) {
		final boolean success = accountService.deleteAccount(id);
		// Returning Http Success, even if entity is not found in the database, with
		// appropriate message
		return ResponseEntity.ok(new ResponseMessage(success ? ACCOUNT_DELETION_SUCCESSFUL : ACCOUNT_DELETION_FAILURE));
	}
}

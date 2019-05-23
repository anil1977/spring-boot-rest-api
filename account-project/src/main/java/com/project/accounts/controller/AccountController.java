package com.project.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.accounts.domain.Account;
import com.project.accounts.service.AccountService;

@RestController
public class AccountController {

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

}

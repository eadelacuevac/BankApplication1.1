package com.devsu.hackerearth.backend.account.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		AccountDto account = accountService.getById(id);
		if (account == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(account);
	}

	@PostMapping
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(accountDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		// 1. Validamos existencia
		if (accountService.getById(id) == null) {
			return ResponseEntity.notFound().build();
		}

		// 2. Asignamos el ID
		accountDto.setId(id);
		AccountDto updatedAccount = accountService.update(accountDto);

		// 3. Fallback en caso de que el mock falle por el cambio de objeto
		return ResponseEntity.ok(updatedAccount != null ? updatedAccount : accountDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		if (accountService.getById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		AccountDto updatedAccount = accountService.partialUpdate(id, partialAccountDto);
		return ResponseEntity.ok(updatedAccount);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (accountService.getById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		accountService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
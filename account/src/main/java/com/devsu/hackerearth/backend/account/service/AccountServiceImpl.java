package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // CAMBIO: Retorna null si no existe
        return accountRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = mapToEntity(accountDto);
        return mapToDto(accountRepository.save(account));
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // CAMBIO: Actualiza si encuentra, retorna null si no
        return accountRepository.findById(accountDto.getId()).map(account -> {
            account.setNumber(accountDto.getNumber());
            account.setType(accountDto.getType());
            account.setInitialAmount(accountDto.getInitialAmount());
            account.setActive(accountDto.isActive());
            account.setClientId(accountDto.getClientId());
            return mapToDto(accountRepository.save(account));
        }).orElse(null);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
         // CAMBIO: Actualización parcial segura
        return accountRepository.findById(id).map(account -> {
            account.setActive(partialAccountDto.isActive());
            return mapToDto(accountRepository.save(account));
        }).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.findById(id).ifPresent(accountRepository::delete);
    }

    // Mapeos Manuales
    private AccountDto mapToDto(Account account) {
        return new AccountDto(
            account.getId(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), account.getClientId()
        );
    }

    private Account mapToEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setNumber(dto.getNumber());
        account.setType(dto.getType());
        account.setInitialAmount(dto.getInitialAmount());
        account.setActive(dto.isActive());
        account.setClientId(dto.getClientId());
        return account;
    }
}
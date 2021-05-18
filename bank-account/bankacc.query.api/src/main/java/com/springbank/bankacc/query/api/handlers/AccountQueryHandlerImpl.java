package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dto.AccountLookupResponse;
import com.springbank.bankacc.query.api.dto.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountHolderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {
    private final AccountRepository accountRepository;

    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        Optional<BankAccount> accountOptional = accountRepository.findById(query.getId());

        if (accountOptional.isEmpty()) {
            return new AccountLookupResponse("No bank account for id " + query.getId());
        }

        return new AccountLookupResponse("Bank account successfully returned", accountOptional.get());
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountHolderIdQuery query) {
        Optional<BankAccount> accountOptional = accountRepository.findByAccountHolderId(query.getAccountHolderId());

        if (accountOptional.isEmpty()) {
            return new AccountLookupResponse("No bank account for holder id " + query.getAccountHolderId());
        }

        return new AccountLookupResponse("Bank account successfully returned", accountOptional.get());
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        var bankAccountIterator = accountRepository.findAll();

        if (!bankAccountIterator.iterator().hasNext()) {
            return new AccountLookupResponse("No bank accounts were found");
        }

        var bankAccounts = new ArrayList<BankAccount>();
        bankAccountIterator.forEach(bankAccounts::add);

        return new AccountLookupResponse(
                String.format("Successfully returned %s bank accounts", bankAccounts.size()),
                bankAccounts
        );
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountsWithBalance(FindAccountsWithBalanceQuery query) {
        List<BankAccount> accounts;

        if (query.getEqualityType() == EqualityType.GREATER_THAN) {
            accounts = accountRepository.findByBalanceGreaterThan(query.getBalance());
        } else {
            accounts = accountRepository.findByBalanceLessThan(query.getBalance());
        }

        if (accounts == null || accounts.isEmpty()) {
            return new AccountLookupResponse("No bank accounts were found");
        }

        return new AccountLookupResponse("Successfully returned " + accounts.size() + " bank accounts", accounts);
    }
}

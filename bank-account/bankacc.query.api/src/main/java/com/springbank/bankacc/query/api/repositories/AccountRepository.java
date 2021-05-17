package com.springbank.bankacc.query.api.repositories;

import com.springbank.bankacc.core.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<BankAccount, String> {
}

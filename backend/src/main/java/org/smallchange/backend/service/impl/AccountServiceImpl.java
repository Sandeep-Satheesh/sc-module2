package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Account;
import org.smallchange.backend.repository.BankAccountRepository;
import org.smallchange.backend.service.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Account}.
 */
@Service
@Transactional
public class AccountServiceImpl implements BankAccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;

    public AccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Account save(Account bankAccount) {
        log.debug("Request to save BankAccount : {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Account update(Account bankAccount) {
        log.debug("Request to update BankAccount : {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Optional<Account> partialUpdate(Account bankAccount) {
        log.debug("Request to partially update BankAccount : {}", bankAccount);

        return bankAccountRepository
            .findById(bankAccount.getAccNo())
            .map(existingBankAccount -> {
                if (bankAccount.getUserId() != null) {
                    existingBankAccount.setUserId(bankAccount.getUserId());
                }
                if (bankAccount.getAccNo() != null) {
                    existingBankAccount.setAccNo(bankAccount.getAccNo());
                }
                if (bankAccount.getBankName() != null) {
                    existingBankAccount.setBankName(bankAccount.getBankName());
                }
                if (bankAccount.getAccType() != null) {
                    existingBankAccount.setAccType(bankAccount.getAccType());
                }
                if (bankAccount.getAccBalance() != null) {
                    existingBankAccount.setAccBalance(bankAccount.getAccBalance());
                }

                return existingBankAccount;
            })
            .map(bankAccountRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        log.debug("Request to get all BankAccounts");
        return bankAccountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findOne(String id) {
        log.debug("Request to get BankAccount : {}", id);
        return bankAccountRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.deleteById(id);
    }
}

package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    @Test
    void successfulAccountCreation() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        // Kick
        String accountId = accountRepository.createAccount("mark", 100.0);
        // Verify
        Assertions.assertNotNull(accountId);
        Assertions.assertEquals(1, accountRepository.getNumberOfAccounts());
        Assertions.assertEquals("mark", accountRepository.getAccount(accountId).name());
    }

    @Test
    void getAccount() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        // Kick
        String accountId = accountRepository.createAccount("andrew", 100.0);
        // Verify
        Assertions.assertEquals("andrew", accountRepository.getAccount(accountId).name());
        Assertions.assertEquals(100.0, accountRepository.getAccount(accountId).amount());
        Assertions.assertNull(accountRepository.getAccount("randomid"));
    }

    @Test
    void deleteAccount() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        String accountId = accountRepository.createAccount("mark", 100.0);
        String accountId2 = accountRepository.createAccount("andrew", 100.0);
        String accountId3 = accountRepository.createAccount("santos", 100.0);
        int initialSize = accountRepository.getNumberOfAccounts();
        // Kick
        accountRepository.deleteAccount(accountId);
        // Verify
        Assertions.assertNotEquals(accountRepository.getNumberOfAccounts(), initialSize);
        Assertions.assertNull(accountRepository.getAccount(accountId));
    }

    @Test
    void getNumberOfAccounts() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        // Kick
        String accountId = accountRepository.createAccount("mark", 100.0);
        String accountId2 = accountRepository.createAccount("andrew", 100.0);
        String accountId3 = accountRepository.createAccount("santos", 100.0);
        int size = accountRepository.getNumberOfAccounts();
        // Verify
        Assertions.assertEquals(3, size);
    }
}
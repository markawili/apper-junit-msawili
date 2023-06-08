package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
        Assertions.assertEquals(100.0, accountRepository.getAccount(accountId).balance());
        Assertions.assertNull(accountRepository.getAccount("randomid"));
    }

    @Test
    void deleteAccount() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        String accountId = accountRepository.createAccount("mark", 100.0);
        accountRepository.createAccount("andrew", 100.0);
        accountRepository.createAccount("santos", 100.0);
        int initialSize = accountRepository.getNumberOfAccounts();
        // Kick
        accountRepository.deleteAccount(accountId);
        // Verify
        Assertions.assertNotEquals(accountRepository.getNumberOfAccounts(), initialSize);
        Assertions.assertNull(accountRepository.getAccount(accountId));
    }

    @Test
    void deleteAccount_AccountNotFound() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        accountRepository.createAccount("mark", 100.0);
        String accountId = "random-id-non-existent";
        // Kick and Verify
        Assertions.assertThrows(IllegalArgumentException    .class,
                () -> accountRepository.deleteAccount(accountId)
        );
    }

    @Test
    void updateAccount() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        String accountId = accountRepository.createAccount("mark", 100.0);
        Account oldAccount = accountRepository.getAccount(accountId);
        // Kick
        Account updatedAccount = new Account(oldAccount.id(), "Andrew", oldAccount.balance());
        accountRepository.updateAccount(updatedAccount);
        Account retrievedUpdatedAccount = accountRepository.getAccount(updatedAccount.id());
        // Verify
        Assertions.assertEquals(oldAccount.id(), retrievedUpdatedAccount.id());
        Assertions.assertEquals(updatedAccount.id(), retrievedUpdatedAccount.id());
        Assertions.assertEquals(updatedAccount.balance(), retrievedUpdatedAccount.balance());
        Assertions.assertEquals(updatedAccount.name(), retrievedUpdatedAccount.name());
    }

    @Test
    void getNumberOfAccounts() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        // Kick
        accountRepository.createAccount("mark", 100.0);
        accountRepository.createAccount("andrew", 100.0);
        accountRepository.createAccount("santos", 100.0);
        int size = accountRepository.getNumberOfAccounts();
        // Verify
        Assertions.assertEquals(3, size);
    }

    @Test
    void noRegisteredAccounts() {
        // Setup
        AccountRepository accountRepository = new AccountRepository();
        //Kick
        boolean isAccountsEmpty = accountRepository.noRegisteredAccounts();
        // Verify
        Assertions.assertTrue(isAccountsEmpty);
    }
}
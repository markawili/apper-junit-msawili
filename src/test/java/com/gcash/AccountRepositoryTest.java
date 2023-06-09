package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AccountRepositoryTest {
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    void successfulAccountCreation() {
        // Kick
        String accountId = accountRepository.createAccount("mark", 100.0);
        // Verify
        Assertions.assertAll("Account creation",
                () -> Assertions.assertNotNull(accountId),
                () -> Assertions.assertEquals(1, accountRepository.getNumberOfAccounts()),
                () -> Assertions.assertEquals("mark", accountRepository.getAccount(accountId).name())
        );

    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void getAccount() {
        // Kick
        String accountId = accountRepository.createAccount("andrew", 100.0);
        // Verify
        Assertions.assertAll("Get Account",
                () -> Assertions.assertEquals("andrew", accountRepository.getAccount(accountId).name()),
                () -> Assertions.assertEquals(100.0, accountRepository.getAccount(accountId).balance()),
                () -> Assertions.assertNull(accountRepository.getAccount("randomid"))
        );

    }

    @Test
    void deleteAccount() {
        // Setup
        String accountId = accountRepository.createAccount("mark", 100.0);
        accountRepository.createAccount("andrew", 100.0);
        accountRepository.createAccount("santos", 100.0);
        int initialSize = accountRepository.getNumberOfAccounts();
        // Kick
        accountRepository.deleteAccount(accountId);
        // Verify
        Assertions.assertAll("Delete Account",
                () -> Assertions.assertNotEquals(accountRepository.getNumberOfAccounts(), initialSize),
                () -> Assertions.assertNull(accountRepository.getAccount(accountId))
        );

    }

    @Test
    void deleteAccount_AccountNotFound() {
        // Setup
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
        String accountId = accountRepository.createAccount("mark", 100.0);
        Account oldAccount = accountRepository.getAccount(accountId);
        // Kick
        Account updatedAccount = new Account(oldAccount.id(), "Andrew", oldAccount.balance());
        accountRepository.updateAccount(updatedAccount);
        Account retrievedUpdatedAccount = accountRepository.getAccount(updatedAccount.id());
        // Verify
        Assertions.assertAll("Update Account",
                () -> Assertions.assertEquals(oldAccount.id(), retrievedUpdatedAccount.id()),
                () -> Assertions.assertEquals(updatedAccount.id(), retrievedUpdatedAccount.id()),
                () -> Assertions.assertEquals(updatedAccount.balance(), retrievedUpdatedAccount.balance()),
                () -> Assertions.assertEquals(updatedAccount.name(), retrievedUpdatedAccount.name())
        );

    }

    @Test
    void getNumberOfAccounts() {
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
        //Kick
        boolean isAccountsEmpty = accountRepository.noRegisteredAccounts();
        // Verify
        Assertions.assertTrue(isAccountsEmpty);
    }
}
package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceServiceTest {
    private AccountRepository accountRepository;
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        balanceService = new BalanceService(accountRepository);
    }

    @Test
    void getBalance() {
        //Setup
        String accountId = accountRepository.createAccount("Mark", 100.0);
        //Kick
        double balance = balanceService.getBalance(accountId);
        //Verify
        Assertions.assertEquals(100.0, balance);
    }

    @Test
    void getBalance_AccountNotFound() {
        //Setup
        String accountId = "random-id-non-existent";
        //Kick and Verify
        Assertions.assertThrows(NullPointerException.class,
            () -> balanceService.getBalance(accountId)
        );
    }

    @Test
    void debit() {
        //Setup
        String accountId = accountRepository.createAccount("Mark", 100.0);
        //Kick
        balanceService.debit(accountId, 50.0);
        Account account = accountRepository.getAccount(accountId);
        //Verify
        Assertions.assertEquals(50.0, account.balance());
    }

    @Test
    void credit() {
        //Setup
        String accountId = accountRepository.createAccount("Mark", 100.0);
        //Kick
        balanceService.credit(accountId, 50.0);
        Account account = accountRepository.getAccount(accountId);
        //Verify
        Assertions.assertEquals(150.0, account.balance());
    }

    @Test
    void transfer() {
        //Setup
        String senderAccountId = accountRepository.createAccount("Mark", 100.0);
        String recipientAccountId = accountRepository.createAccount("Andrew", 100.0);
        //Kick
        balanceService.transfer(senderAccountId, recipientAccountId, 50.0);
        Account senderAccount = accountRepository.getAccount(senderAccountId);
        Account recipientAccount = accountRepository.getAccount(recipientAccountId);
        //Verify
        Assertions.assertAll("Transfer",
                () -> Assertions.assertEquals(senderAccount.balance(), 50.0),
                () -> Assertions.assertEquals(recipientAccount.balance(), 150.0)
        );
    }

    @Test
    void transfer_InsufficientBalance() {
        //Setup
        String senderAccountId = accountRepository.createAccount("Mark", 100.0);
        String recipientAccountId = accountRepository.createAccount("Andrew", 100.0);
        //Kick and Verify
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceService.transfer(senderAccountId, recipientAccountId, 101.0)
        );
    }
}
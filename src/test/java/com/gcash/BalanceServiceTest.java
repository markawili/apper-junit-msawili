package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceServiceTest {

    @Test
    void getBalance() {
        //Setup
        AccountRepository accountRepository = new AccountRepository();
        BalanceService balanceService = new BalanceService(accountRepository);
        String accountId = accountRepository.createAccount("Mark", 100.0);
        //Kick
        double balance = balanceService.getBalance(accountId);
        //Verify
        Assertions.assertEquals(100.0, balance);
    }

    @Test
    void debit() {
        //Setup
        AccountRepository accountRepository = new AccountRepository();
        BalanceService balanceService = new BalanceService(accountRepository);
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
        AccountRepository accountRepository = new AccountRepository();
        BalanceService balanceService = new BalanceService(accountRepository);
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
        AccountRepository accountRepository = new AccountRepository();
        BalanceService balanceService = new BalanceService(accountRepository);
        String senderAccountId = accountRepository.createAccount("Mark", 100.0);
        String recipientAccountId = accountRepository.createAccount("Andrew", 100.0);
        //Kick
        balanceService.transfer(senderAccountId, recipientAccountId, 50.0);
        Account senderAccount = accountRepository.getAccount(senderAccountId);
        Account recipientAccount = accountRepository.getAccount(recipientAccountId);
        //Verify
        Assertions.assertEquals(senderAccount.balance(), 50.0);
        Assertions.assertEquals(recipientAccount.balance(), 150.0);
    }
}
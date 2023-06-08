package com.gcash;

public class BalanceService {
    private final AccountRepository accountRepository;

    BalanceService(AccountRepository accRepository) {
        this.accountRepository = accRepository;
    }

    public Double getBalance(String id) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            return account.balance();
        }
        return null;
    }

    public void debit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            double newBalance = account.balance() - amount;
            Account updatedAccount = new Account(account.id(), account.name(), newBalance);
            accountRepository.updateAccount(updatedAccount);
        }
    }

    public void credit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            double newBalance = account.balance() + amount;
            Account updatedAccount = new Account(account.id(), account.name(), newBalance);
            accountRepository.updateAccount(updatedAccount);
        }
    }

    public void transfer(String from, String to, Double amount) {
        Account sender = accountRepository.getAccount(from);
        Account recipient = accountRepository.getAccount(to);

        if (sender != null && recipient != null) {
            if (sender.balance() >= amount) {
                Double newBalanceSender = sender.balance() - amount;
                Double newBalanceRecipient = recipient.balance() + amount;

                Account updatedAccountFrom = new Account(sender.id(), sender.name(), newBalanceSender);
                Account updatedAccountTo = new Account(recipient.id(), recipient.name(), newBalanceRecipient);

                accountRepository.updateAccount(updatedAccountFrom);
                accountRepository.updateAccount(updatedAccountTo);
            }
        }
    }
}

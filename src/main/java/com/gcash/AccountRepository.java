package com.gcash;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    public String createAccount(String name, Double initialBalance) {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, name, initialBalance);

        accounts.add(account);

        return id;
    }

    public Account getAccount(String id) {

        for (Account account: accounts) {
            if (account.id().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public void deleteAccount(String id) {
        for (Account account: accounts) {
            if (account.id().equals(id)) {
                accounts.remove(account);
                return;
            }
        }
    }

    public void updateAccount(Account updatedAccount) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.id().equals(updatedAccount.id())) {
                accounts.set(i, updatedAccount);
                return;
            }
        }
    }

    public Integer getNumberOfAccounts() {
        return accounts.size();
    }
}

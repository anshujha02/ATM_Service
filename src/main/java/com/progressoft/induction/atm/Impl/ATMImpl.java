package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMImpl implements ATM {
    private final BankingSystemImpl bankingSystem=new BankingSystemImpl();
    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);

        if (accountBalance == null) {
            throw new AccountNotFoundException("Account not found");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (amount.compareTo(accountBalance) > 0) {
            throw new InsufficientFundsException("Insufficient funds in the account");
        }

        BigDecimal atmBalance = bankingSystem.sumOfMoneyInAtm();
        if (amount.compareTo(atmBalance) > 0) {
            throw new NotEnoughMoneyInATMException("Not enough money in the ATM");
        }

        List<Banknote> Banknotes = new ArrayList<>();

        bankingSystem.debitAccount(accountNumber, amount);

        return Banknotes;
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}

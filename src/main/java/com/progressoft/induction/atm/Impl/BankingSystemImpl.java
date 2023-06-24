package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BankingSystemImpl implements BankingSystem {
   Map<String, BigDecimal> accountBalanceMap = new HashMap<String, BigDecimal>();
   EnumMap<Banknote,Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public BankingSystemImpl() {
        atmCashMap.put(Banknote.FIFTY_JOD,10);
        atmCashMap.put(Banknote.TWENTY_JOD,20);
        atmCashMap.put(Banknote.TEN_JOD,100);
        atmCashMap.put(Banknote.FIVE_JOD,100);

        accountBalanceMap.put("123456789", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("111111111", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("222222222", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("333333333", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("444444444", BigDecimal.valueOf(1000.0));
    }
    public BigDecimal sumOfMoneyInAtm() {
        BigDecimal atmBalance = BigDecimal.ZERO;
        for (Banknote banknote : atmCashMap.keySet()) {
            int banknoteQuantity = atmCashMap.get(banknote);
            BigDecimal banknoteValue = banknote.getValue();
            BigDecimal banknoteTotal = banknoteValue.multiply(BigDecimal.valueOf(banknoteQuantity));
            atmBalance = atmBalance.add(banknoteTotal);
        }
        return atmBalance;
    }

    public int getBanknoteQuantity(Banknote banknote) {
        return atmCashMap.getOrDefault(banknote, 0);
    }

    public void decreaseBanknoteQuantity(Banknote banknote, int quantity) {
        int currentQuantity = atmCashMap.getOrDefault(banknote, 0);
        int newQuantity = Math.max(currentQuantity - quantity, 0);
        atmCashMap.put(banknote, newQuantity);
    }

    public void increaseBanknoteQuantity(Banknote banknote, int quantity) {
        int currentQuantity = atmCashMap.getOrDefault(banknote, 0);
        int newQuantity = currentQuantity + quantity;
        atmCashMap.put(banknote, newQuantity);
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        BigDecimal accountBalance = accountBalanceMap.get(accountNumber);
        if (accountBalance == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return accountBalance;
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance = accountBalanceMap.get(accountNumber);
        if (accountBalance == null) {
            throw new AccountNotFoundException("Account not found");
        }
        BigDecimal newBalance = accountBalance.subtract(amount);
        accountBalanceMap.put(accountNumber, newBalance);
    }
}

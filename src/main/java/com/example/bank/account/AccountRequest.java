package com.example.bank.account;

import java.util.List;

public record AccountRequest(Integer customerId, String country, List<String> currencies) {

}

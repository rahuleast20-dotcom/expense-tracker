package com.example.inventory.dto;
public record ExpenseRequest(String title,Integer day,Double amount,String category,String remarks){}
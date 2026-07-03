package com.example.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Integer>{

    List<Expense> findByTitleContainingIgnoreCase(String keyword);

    List<Expense> findByCategory(String category);

    List<Expense> findByTitleContainingIgnoreCaseAndCategory(
            String keyword,
            String category);

}
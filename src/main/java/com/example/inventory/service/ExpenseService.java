package com.example.inventory.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.inventory.entity.Expense;
import com.example.inventory.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public void save(Expense expense) {
        repo.save(expense);
    }

    public List<Expense> all() {
        return repo.findAll();
    }

    public Expense getById(Integer id) {

        return repo.findById(id).orElseThrow();
    }
    
    public Double getTotalSpending() {

        return repo.findAll()
                .stream()
                .map(Expense::getAmount)
                .filter(a -> a != null)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public long getTotalItems() {
        return repo.count();
    }

    public Map<String, Double> getCategoryWiseSpending() {

        return repo.findAll()
                .stream()
                .filter(e -> e.getCategory() != null)
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
    public String getTopCategory() {

        return getCategoryWiseSpending()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (₹" + e.getValue() + ")")
                .orElse("No Data");
    }
    
    public Map<String, Double> getCategorySummary() {

        return repo.findAll()
                .stream()
                .filter(e -> e.getCategory() != null)
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
    
    public List<Expense> search(String keyword) {
        return repo.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Expense> search(String keyword, String category) {

        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasCategory = category != null &&
                              !category.isBlank() &&
                              !"All".equals(category);

        List<Expense> result;

        if (hasKeyword && hasCategory) {
            result = repo.findByTitleContainingIgnoreCaseAndCategory(keyword, category);
        } else if (hasKeyword) {
            result = repo.findByTitleContainingIgnoreCase(keyword);
        } else if (hasCategory) {
            result = repo.findByCategory(category);
        } else {
            result = repo.findAll();
        }

        System.out.println("Result Size = " + result.size());

        result.forEach(e ->
            System.out.println(e.getTitle() + " | " + e.getCategory()));

        return result;
    }
}
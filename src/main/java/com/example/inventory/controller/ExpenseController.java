
package com.example.inventory.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.inventory.entity.Expense;
import com.example.inventory.service.ExpenseService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpenseController
{
	private final ExpenseService service;
	public ExpenseController(ExpenseService service)
	{
		this.service=service;
	}
	
	@GetMapping("/")
	public String home(@RequestParam(required=false) String keyword, @RequestParam(required=false) String category,Model model)
	{
	    if (!model.containsAttribute("expense")) {
	        model.addAttribute("expense", new Expense());
	    }
	    model.addAttribute("expenses",service.search(keyword,category));
	    model.addAttribute("keyword",keyword);
	    model.addAttribute("category",category);
	    model.addAttribute("topCategory", service.getTopCategory());
	    model.addAttribute("totalSpending",service.getTotalSpending());
	    model.addAttribute("totalItems",service.getTotalItems());
	    model.addAttribute( "categorySummary",service.getCategorySummary());
	    return "index";
	}

	@GetMapping("/edit/{id}")
	public String editExpense(@PathVariable Integer id,
	                          Model model) {

	    Expense expense = service.getById(id);

	    model.addAttribute("expense", expense);

	    model.addAttribute("expenses", service.all());

	    model.addAttribute("totalItems", service.getTotalItems());

	    model.addAttribute("totalSpending", service.getTotalSpending());

	    model.addAttribute("topCategory", service.getTopCategory());

	    model.addAttribute("categorySummary",
	            service.getCategorySummary());

	    return "index";
	}
	
	@PostMapping("/save")
	public String save( @Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model model,
	    RedirectAttributes redirectAttributes) 
	{
	    if(result.hasErrors()) {
	        model.addAttribute("expenses",service.all());
	        model.addAttribute("totalSpending",service.getTotalSpending());
	        model.addAttribute("totalItems",service.getTotalItems());
	        model.addAttribute("categorySummary", service.getCategorySummary());
	        return "index";
	    }
	    service.save(expense);
	    redirectAttributes.addFlashAttribute(
	            "successMessage",
	            "Expense Saved Successfully!");
	    return "redirect:/";
	}
}

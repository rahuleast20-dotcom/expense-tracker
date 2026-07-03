package com.example.inventory.api;

import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.inventory.entity.Expense;
import com.example.inventory.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {
  private final ExpenseService service;
  public ExpenseRestController(ExpenseService service){this.service=service;}

  @GetMapping
  public List<Expense> all(){ return service.all(); }

  @GetMapping("/{id}")
  public ResponseEntity<Expense> one(@PathVariable Integer id){
    try{return ResponseEntity.ok(service.findById(id));}
    catch(Exception e){return ResponseEntity.notFound().build();}
  }


  @PostMapping
  public ResponseEntity<Expense> create(@Valid @RequestBody Expense e) {
      Expense saved = service.save(e);
      return ResponseEntity.status(HttpStatus.CREATED)
                           .body(saved);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Expense> update(@PathVariable Integer id,@RequestBody Expense e){
    try{
      Expense ex=service.findById(id);
      ex.setTitle(e.getTitle());
      ex.setDay(e.getDay());
      ex.setAmount(e.getAmount());
      ex.setCategory(e.getCategory());
      service.save(ex);
      return ResponseEntity.ok(ex);
    }catch(Exception x){return ResponseEntity.notFound().build();}
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id){
    try{service.deleteById(id); return ResponseEntity.noContent().build();}
    catch(Exception e){return ResponseEntity.notFound().build();}
  }
}

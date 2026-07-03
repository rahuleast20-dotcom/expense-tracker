package com.example.inventory.exception;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import java.util.*;
@ControllerAdvice public class GlobalExceptionHandler{
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String,String>> handle(MethodArgumentNotValidException ex){
Map<String,String> m=new HashMap<>();
ex.getBindingResult().getFieldErrors().forEach(e->m.put(e.getField(),e.getDefaultMessage()));
return ResponseEntity.badRequest().body(m);}
}
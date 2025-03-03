package com.groupeisi.gestionproduits.controller;

import com.groupeisi.gestionproduits.exception.ProductNotFoundException;
import com.groupeisi.gestionproduits.model.Product;
import com.groupeisi.gestionproduits.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductApiController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid
            @RequestBody
            Product product
    ) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.getProductsByKeyword(keyword));
    }

    @GetMapping("/{reference}")
    public ResponseEntity<Product> getProductByReference(@PathVariable String reference) {
        return ResponseEntity.ok(productService.getProductByReference(reference));
    }

    @DeleteMapping("/{reference}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String reference) {
        productService.deleteProduct(reference);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reference}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable
            String reference,
            @Valid
            @RequestBody
            Product product
    ) {
        return ResponseEntity.ok(productService.updateProduct(reference, product));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
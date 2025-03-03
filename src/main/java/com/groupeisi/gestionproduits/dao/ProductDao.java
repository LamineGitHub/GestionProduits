package com.groupeisi.gestionproduits.dao;

import com.groupeisi.gestionproduits.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("productDaoHashMap")
public class ProductDao implements IProductDao {

    private final Map<String, Product> products = new HashMap<>();

    public ProductDao() {
        products.put("REF001", new Product("REF001", "Smartphone Galaxy S21", 80000, 2));
        products.put("REF002", new Product("REF002", "Ordinateur portable ThinkPad X1", 120000, 14));
        products.put("REF003", new Product("REF003", "Écouteurs sans fil", 2500, 20));
        products.put("REF004", new Product("REF004", "Tablette iPad Pro", 100000, 7));
    }

    @Override
    public Product save(Product product) {
        products.put(product.getReference(), product);
        return product;
    }

    @Override
    public Optional<Product> findByReference(String reference) {
        return Optional.ofNullable(products.get(reference));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findByDesignationContaining(String keyword) {
        return products.values().stream()
                .filter(p -> p.getDesignation().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByReference(String reference) {
        products.remove(reference);
    }

    @Override
    public boolean existsByReference(String reference) {
        return products.containsKey(reference);
    }

    @Override
    public boolean existsByDesignation(String name) {
        return products.containsKey(name);
    }
}
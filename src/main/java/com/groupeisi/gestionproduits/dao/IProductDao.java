package com.groupeisi.gestionproduits.dao;

import com.groupeisi.gestionproduits.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductDao {
    Product save(Product product);

    Optional<Product> findByReference(String reference);

    List<Product> findAll();

    List<Product> findByDesignationContaining(String keyword);

    void deleteByReference(String reference);

    boolean existsByReference(String reference);

    boolean existsByDesignation(String name);
}
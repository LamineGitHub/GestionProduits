package com.groupeisi.gestionproduits.service;

import com.groupeisi.gestionproduits.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);

    Product getProductByReference(String reference);

    List<Product> getAllProducts();

    List<Product> getProductsByKeyword(String keyword);

    void deleteProduct(String reference);

    Product updateProduct(String reference, Product product);

    boolean existsByName(String name);
    
    // Ajout de cette méthode
    boolean existsByReference(String reference);
}
package com.groupeisi.gestionproduits.service;

import com.groupeisi.gestionproduits.dao.IProductDao;
import com.groupeisi.gestionproduits.exception.ProductNotFoundException;
import com.groupeisi.gestionproduits.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductDao productDao;

    public ProductService(@Qualifier("productDaoHashMap") IProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product addProduct(Product product) {
        if (productDao.existsByReference(product.getReference())) {
            throw new IllegalArgumentException("Un produit avec cette référence existe déjà");
        }
        return productDao.save(product);
    }

    @Override
    public Product getProductByReference(String reference) {
        return productDao.findByReference(reference)
                .orElseThrow(() -> new ProductNotFoundException("Produit non trouvé avec la référence: " + reference));
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        return productDao.findByDesignationContaining(keyword);
    }

    @Override
    public void deleteProduct(String reference) {
        if (!productDao.existsByReference(reference)) {
            throw new ProductNotFoundException("Produit non trouvé avec la référence: " + reference);
        }
        productDao.deleteByReference(reference);
    }

    @Override
    public Product updateProduct(String reference, Product product) {
        if (!productDao.existsByReference(reference)) {
            throw new ProductNotFoundException("Produit non trouvé avec la référence: " + reference);
        }
        product.setReference(reference);
        return productDao.save(product);
    }

    @Override
    public boolean existsByName(String name) {
        return productDao.existsByDesignation(name);
    }
    
    @Override
    public boolean existsByReference(String reference) {
        return productDao.existsByReference(reference);
    }
}
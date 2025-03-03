package com.groupeisi.gestionproduits.dao;

import com.groupeisi.gestionproduits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productDaoDatabase")
public interface ProductDaoDb extends JpaRepository<Product, String>, IProductDao {

    @Override
    List<Product> findByDesignationContaining(String keyword);

    @Override
    default boolean existsByReference(String reference) {
        return existsById(reference);
    }

    @Override
    default void deleteByReference(String reference) {
        deleteById(reference);
    }
}
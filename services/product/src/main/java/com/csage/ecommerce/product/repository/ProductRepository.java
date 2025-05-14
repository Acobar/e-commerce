package com.csage.ecommerce.product.repository;

import com.csage.ecommerce.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer categoryId);

    List<Product> findByIdInOrderById(List<Integer> productIds);
}

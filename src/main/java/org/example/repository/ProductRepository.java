package org.example.repository;

import org.example.entity.Product;
import org.example.exceptions.DuplicateIdException;
import org.example.exceptions.InventoryException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product addProduct(Product product) throws InventoryException;
    Product updateProduct(Product product, Integer productId);
    void deleteProductById(Integer productId);
    Optional<Product> findProductById(Integer productId);
    List<Product> findDiscountedProducts(Optional<Double> discountMin, Optional<Double> discountMax);
    List<Product> findAll();
    void deleteAllProducts ();
}

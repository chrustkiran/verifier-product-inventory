package org.example.repository;

import org.example.entity.Product;
import org.example.exceptions.BadArgumentsException;
import org.example.exceptions.DuplicateIdException;
import org.example.exceptions.InventoryException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    /**
     *
     * @param product
     * @return Product
     * @throws BadArgumentsException
     */
    Product addProduct(Product product);
    Product updateProduct(Product product, Integer productId);
    List<Product> deleteProductById(Integer productId);
    Optional<Product> findProductById(Integer productId);
    List<Product> findDiscountedProducts(Optional<Double> discountMin, Optional<Double> discountMax);
    List<Product> findAll();
    void deleteAllProducts ();
}

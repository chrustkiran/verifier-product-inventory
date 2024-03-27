package org.example.repository;

import org.example.constants.ErrorMessage;
import org.example.entity.Product;
import org.example.exceptions.*;
import org.example.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductRepositoryImpl implements ProductRepository {
    //empty unmodifiable list to avoid checking the null everywhere from the start
    private List<Product> productList = Collections.unmodifiableList(new ArrayList<>());

    @Override
    public Product addProduct(Product product) throws InventoryException {
        //checking null values
        ValidationUtils.checkValidProduct(product);
        if(isProductIdExisting(product.getProductId())) throw new DuplicateIdException(String.format
                (ErrorMessage.DUPLICATE_ID, product.getProductId()));
        productList = Stream.concat(productList.stream(), Stream.of(product)).collect(Collectors.toUnmodifiableList());
        return productList.get(productList.size()-1);
    }

    @Override
    public synchronized Optional<Product> findProductById(Integer productId) {
        ValidationUtils.checkValidProductId(productId);
        return productList.stream().filter(existingProduct -> existingProduct.getProductId().equals(productId)).findFirst();
    }

    @Override
    public synchronized void deleteAllProducts() {
        productList = Collections.unmodifiableList(new ArrayList<>());
    }

    @Override
    public Product updateProduct(Product product, Integer productId) throws InventoryException {
        //check null for product and productId
        ValidationUtils.checkValidProduct(product);
        ValidationUtils.checkValidProductId(productId);
        if(!isProductIdExisting(productId)) throw new NoRecordFoundException(String.format
                (ErrorMessage.NO_RECORD_FOUND_EXCEPTION, product.getProductId()));
        productList = Stream.concat(productList.stream().filter(existingProduct ->
                !existingProduct.getProductId().equals(product.getProductId())), Stream.of(product))
                .collect(Collectors.toUnmodifiableList());
        return productList.get(productList.size()-1);
    }

    @Override
    public synchronized void deleteProductById(Integer productId) {
        //null check for productId
        ValidationUtils.checkValidProductId(productId);
        if (!isProductIdExisting(productId)) { throw new NoRecordFoundException(String.format
                    (ErrorMessage.NO_RECORD_FOUND_EXCEPTION, productId));
        }
        productList = productList.stream().filter(existingProduct -> !existingProduct.getProductId().
                equals(productId)).collect(Collectors.toUnmodifiableList());
    }



    @Override
    public synchronized List<Product> findDiscountedProducts(Optional<Double> discountMin, Optional<Double> discountMax) {
        return productList.stream().filter(existingProduct -> existingProduct.getDiscount().isPresent()).
                filter(existingProduct ->
                existingProduct.getDiscount().get().compareTo(discountMin.orElse(Double.MIN_VALUE)) > 0
                        && existingProduct.getDiscount().get() <= discountMax.orElse(Double.MAX_VALUE))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findAll() {
        return new ArrayList<>(productList);
    }


    private synchronized boolean isProductIdExisting(Integer productId) {
        ValidationUtils.checkValidProductId(productId);
        return Optional.ofNullable(productList).orElse(Collections.emptyList()).stream().anyMatch(existingProduct ->
                existingProduct.getProductId().equals(productId));
    }
}

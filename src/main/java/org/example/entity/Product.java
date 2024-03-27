package org.example.entity;

import org.example.enums.Category;
import org.example.exceptions.BadArgumentsException;

import java.util.Optional;

//it should be an immutable object since all the fields are final and there are no setters
//since there is no mutable type's field, I didn't expose any deep copy.
//exposing through getter with mutable objects, it won't affect the immutability of the object.
public class Product {
    private final Integer productId;
    private final String name;
    private final Category category;
    private final Double price;
    private final Optional<Double> discount;

    public Product(Integer productId, String name, Category category, Double price, Double discount) {
        isOneOfMandatoryArgsNull(productId, name, price);
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = Optional.ofNullable(discount);
    }

    public Product(Integer productId, String name, Category category, Double price) {
        isOneOfMandatoryArgsNull(productId, name, price);
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = Optional.empty();
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public Optional<Double> getDiscount() {
        return discount;
    }

    private void isOneOfMandatoryArgsNull(Integer productId, String name, Double price) {
        if(productId == null || name == null || price == null) {
            throw new BadArgumentsException("Mandatory args are null");
        }
    }
}

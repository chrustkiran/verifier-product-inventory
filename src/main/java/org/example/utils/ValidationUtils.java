package org.example.utils;

import org.example.entity.Product;
import org.example.exceptions.BadArgumentsException;

public interface ValidationUtils {
      static void checkValidProduct(Product product) {
        //we can perform some argument check here related to product
        if (product == null) {
            throw new BadArgumentsException("Product is null");
        }
    }
     static void checkValidProductId(Integer productId) {
        //we can perform some argument check here related to product id
        if (productId == null) {
            throw new BadArgumentsException("ProductId is null");
        }
    }
}

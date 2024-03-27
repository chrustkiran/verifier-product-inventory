package entity;

import org.example.entity.Product;
import org.example.enums.Category;
import org.example.exceptions.BadArgumentsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void createValidProductWithDiscount() {
        String name ="Computer";
        Product product = new Product(1, name, Category.PRODUCT_1, 1000.0, 0.05);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getName(), name);
        assertEquals(product.getCategory(), Category.PRODUCT_1);
        assertEquals(product.getPrice(), 1000.0);
        assertTrue(product.getDiscount().isPresent());
        assertEquals(product.getDiscount().get(), 0.05);
    }

    @Test
    public void createValidProductWithoutDiscountUsingSecondConstructor() {
        String name ="Computer";
        Product product = new Product(1, name, Category.PRODUCT_1, 1000.0);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getName(), name);
        assertEquals(product.getCategory(), Category.PRODUCT_1);
        assertEquals(product.getPrice(), 1000.0);
        assertTrue(product.getDiscount().isEmpty());
    }

    @Test
    public void createValidProductWithoutDiscountUsingFirstConstructor() {
        String name ="Computer";
        Product product = new Product(1, name, Category.PRODUCT_1, 1000.0, null);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getName(), name);
        assertEquals(product.getCategory(), Category.PRODUCT_1);
        assertEquals(product.getPrice(), 1000.0);
        assertTrue(product.getDiscount().isEmpty());
    }

    @Test
    public void creatingInvalidProductShoudThrowError() {
        String name ="Computer";
        Exception exception = assertThrows(BadArgumentsException.class, () -> {
            new Product(null, name, Category.PRODUCT_1, 1000.0, null);
        });
        assertEquals(exception.getMessage(), "Mandatory args are null");
    }
}

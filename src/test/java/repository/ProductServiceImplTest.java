package repository;

import org.example.entity.Product;
import org.example.enums.Category;
import org.example.exceptions.BadArgumentsException;
import org.example.exceptions.DuplicateIdException;
import org.example.exceptions.NoRecordFoundException;
import org.example.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest {
    @InjectMocks
    ProductRepositoryImpl productRepository;

    AutoCloseable mocks;
    @BeforeEach
    public void init() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void clear() throws Exception {
        mocks.close();
        productRepository.deleteAllProducts();
    }

    @Test
    public void addProductWithValidId() {
        //creating a product
        Product product = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        //adding product
        productRepository.addProduct(product);
        Optional<Product> productOpt = productRepository.findProductById(1);
        assertEquals(productOpt, Optional.of(product));
    }


    @Test
    public void addProductWithEmptyValue() {
        Product product = null;
        Exception exception = assertThrows(BadArgumentsException.class, () -> {
            productRepository.addProduct(product);
        });
        assertEquals(exception.getMessage(), "Product is null");
    }

    @Test()
    public void addProductWithDuplicateValidId() {
        Product product = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        productRepository.addProduct(product);
        Exception exception = assertThrows(DuplicateIdException.class, () -> {
            productRepository.addProduct(product);
        });
        assertTrue(exception.getMessage().contains("This id 1 already exists"));
    }

    @Test
    public void findByIdWithValidIdShouldHaveResult() {
        Product product = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        Product addedProduct = productRepository.addProduct(product);
        Optional<Product> foundProduct = productRepository.findProductById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals(addedProduct.getProductId(), foundProduct.get().getProductId());
    }

    @Test
    public void findByIdWithInValidIdShouldHaveEmpty() {
        Optional<Product> foundProduct = productRepository.findProductById(1);
        assertTrue(foundProduct.isEmpty());
    }

    @Test
    public void deleteAllShouldEmptyTheList() {
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        productRepository.addProduct(product1);
        productRepository.deleteAllProducts();
        assertTrue(productRepository.findAll().size() == 0);
    }

    @Test
    public void updateExistingProductShouldBeSuccessful() {
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        productRepository.addProduct(product1);
        Product product2 = new Product(1, "Product 1", Category.PRODUCT_2, 505.21);
        productRepository.updateProduct(product2, 1);
        Optional<Product> retrievedProduct = productRepository.findProductById(1);
        assertTrue(retrievedProduct.isPresent());
        assertEquals(product2.getProductId(), retrievedProduct.get().getProductId());
        assertEquals(product2.getPrice(), retrievedProduct.get().getPrice());
        assertEquals(product2.getCategory(), retrievedProduct.get().getCategory());
    }

    @Test
    public void updateNonExistingProductShouldThrowError() {
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        Exception exception = assertThrows(NoRecordFoundException.class, () -> {
            productRepository.updateProduct(product1, 1);
        });
        assertEquals(exception.getMessage(), "No Record Found with the Id 1");
    }

    @Test
    public void updateWithNullProductIdShouldThrowError() {
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 500.21);
        Exception exception = assertThrows(BadArgumentsException.class, () -> {
            productRepository.updateProduct(product1, null);
        });
        assertEquals(exception.getMessage(), "ProductId is null");
    }

    @Test
    public void deleteProductShouldBeSuccessful() {
        Product product = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.5);
        productRepository.addProduct(product);
        assertEquals(productRepository.findAll().size(), 1);
        productRepository.deleteProductById(1);
        assertEquals(productRepository.findAll().size(), 0);
    }

    @Test
    public void deleteWithNullProductIdShouldThrowError() {
        Exception exception = assertThrows(BadArgumentsException.class, () -> {
            productRepository.deleteProductById(null);
        });
        assertEquals(exception.getMessage(), "ProductId is null");
    }

    @Test
    public void findAllShouldReturnAllTheProducts() {
        //creating dummy products
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.25);
        Product product2 = new Product(2, "Product 2", Category.PRODUCT_2, 100.0, 0.35);
        Product product3 = new Product(3, "Product 3", Category.PRODUCT_1, 100.0, 0.15);
        Product product4 = new Product(4, "Product 4", Category.PRODUCT_2, 100.0, 0.05);

        //adding products one by one.
        //good to implement addAllProduct  but for the simplicity I didn't implement it yet.
        Product addedProduct1 = productRepository.addProduct(product1);
        Product addedProduct2 = productRepository.addProduct(product2);
        Product addedProduct3 = productRepository.addProduct(product3);
        Product addedProduct4 = productRepository.addProduct(product4);

        String addedProductIds = Arrays.asList(addedProduct1, addedProduct2, addedProduct3, addedProduct4).stream()
                        .map(product -> product.getProductId().toString())
                .collect(Collectors.joining("-"));
        String fetchedProducts = productRepository.findAll().stream().map(product -> product.getProductId().toString())
                        .collect(Collectors.joining("-"));
        assertEquals(addedProductIds, fetchedProducts);
    }

    @Test
    public void deleteProductWithNonExistingIdShouldFail() {
        Exception exception = assertThrows(NoRecordFoundException.class, () -> {
            productRepository.deleteProductById(1);
        });
        assertEquals(exception.getMessage(), "No Record Found with the Id 1");
    }

    @Test
    public void verifyDiscountedProductsWithMinAndMax() {
        //creating dummy products
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.25);
        Product product2 = new Product(2, "Product 2", Category.PRODUCT_2, 100.0, 0.35);
        Product product3 = new Product(3, "Product 3", Category.PRODUCT_1, 100.0, 0.15);
        Product product4 = new Product(4, "Product 4", Category.PRODUCT_2, 100.0, 0.05);

        //adding products one by one.
        //good to implement addAllProduct  but for the simplicity I didn't implement it yet.
        productRepository.addProduct(product1);
        productRepository.addProduct(product2);
        productRepository.addProduct(product3);
        productRepository.addProduct(product4);

        List<Product> productList = productRepository.findDiscountedProducts(Optional.of(0.06), Optional.of(0.3));

        String discountedIds = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.joining("-"));
        //since we have the products all in the specified range except second one and last one
        assertEquals(discountedIds, "1-3");
    }

    @Test
    public void verifyDiscountedProductsWithMinAndNoMax() {
        //creating dummy products
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.25);
        Product product2 = new Product(2, "Product 2", Category.PRODUCT_2, 100.0, 0.35);
        Product product3 = new Product(3, "Product 3", Category.PRODUCT_1, 100.0, 0.15);
        Product product4 = new Product(4, "Product 4", Category.PRODUCT_2, 100.0, 0.05);

        //adding products one by one.
        //good to implement addAllProduct  but for the simplicity I didn't implement it yet.
        productRepository.addProduct(product1);
        productRepository.addProduct(product2);
        productRepository.addProduct(product3);
        productRepository.addProduct(product4);

        List<Product> productList = productRepository.findDiscountedProducts(Optional.of(0.06), Optional.empty());

        String discountedIds = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.joining("-"));
        //since we have the products all in the specified range except last one
        assertEquals(discountedIds, "1-2-3");
    }

    @Test
    public void verifyDiscountedProductsWithMaxAndNoMin() {
        //creating dummy products
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.25);
        Product product2 = new Product(2, "Product 2", Category.PRODUCT_2, 100.0, 0.35);
        Product product3 = new Product(3, "Product 3", Category.PRODUCT_1, 100.0, 0.15);
        Product product4 = new Product(4, "Product 4", Category.PRODUCT_2, 100.0, 0.05);

        //adding products one by one.
        //good to implement addAllProduct  but for the simplicity I didn't implement it yet.
        productRepository.addProduct(product1);
        productRepository.addProduct(product2);
        productRepository.addProduct(product3);
        productRepository.addProduct(product4);

        List<Product> productList = productRepository.findDiscountedProducts(Optional.empty(), Optional.of(0.3));

        String discountedIds = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.joining("-"));
        //since we have the products all in the specified range except second one
        assertEquals(discountedIds, "1-3-4");
    }

    @Test
    public void verifyDiscountedProductsWithNoMaxAndNoMin() {
        //creating dummy products
        Product product1 = new Product(1, "Product 1", Category.PRODUCT_1, 100.0, 0.25);
        Product product2 = new Product(2, "Product 2", Category.PRODUCT_2, 100.0, 0.35);
        Product product3 = new Product(3, "Product 3", Category.PRODUCT_1, 100.0, 0.15);
        Product product4 = new Product(4, "Product 4", Category.PRODUCT_2, 100.0, 0.05);
        Product product5 = new Product(5, "Product 5", Category.PRODUCT_1, 100.0);
        Product product6 = new Product(6, "Product 6", Category.PRODUCT_2, 100.0);

        //adding products one by one.
        //good to implement addAllProduct  but for the simplicity I didn't implement it yet.
        productRepository.addProduct(product1);
        productRepository.addProduct(product2);
        productRepository.addProduct(product3);
        productRepository.addProduct(product4);

        List<Product> productList = productRepository.findDiscountedProducts(Optional.empty(), Optional.empty());

        String discountedIds = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.joining("-"));
        //since we have the products all in the specified range except 5th and 6th since it doesn't have any discount
        assertEquals(discountedIds, "1-2-3-4");
    }
}

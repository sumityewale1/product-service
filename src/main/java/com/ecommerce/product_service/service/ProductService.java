package com.ecommerce.product_service.service;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        LOGGER.info("Fetching all products from the database");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        LOGGER.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found with id: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });
    }

    public Product createProduct(Product product) {
        LOGGER.info("Creating new product: {}", product.getName());
        Product savedProduct = productRepository.save(product);
        LOGGER.info("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(Long id, Product productDetails) {
        LOGGER.info("Updating product with id: {}", id);
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        Product updatedProduct = productRepository.save(product);
        LOGGER.info("Product updated successfully with id: {}", updatedProduct.getId());
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        LOGGER.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
        LOGGER.info("Product deleted successfully with id: {}", id);
    }

    public List<Product> create100Products(List<Product> products) {
        LOGGER.info("Bulk inserting {} products", products.size());
        List<Product> savedProducts = productRepository.saveAll(products);
        LOGGER.info("Bulk insert completed successfully");
        return savedProducts;
    }
}

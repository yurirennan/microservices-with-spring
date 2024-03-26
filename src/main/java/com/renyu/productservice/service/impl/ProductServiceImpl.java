package com.renyu.productservice.service.impl;

import com.renyu.productservice.dto.CreateProductDTO;
import com.renyu.productservice.dto.ProductDTO;
import com.renyu.productservice.exceptions.ProductNotFoundException;
import com.renyu.productservice.model.Product;
import com.renyu.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(final CreateProductDTO productDTO) {

        final Product product = productDTO.toProduct();

        this.productRepository.save(product);
    }

    @Override
    public ProductDTO getProduct(final String productId) {
        final Optional<Product> productOptional = this.productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("PRODUCT NOT FOUND!");
        }

        final Product product = productOptional.get();

        return ProductDTO.fromProduct(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        final List<Product> allProducts = this.productRepository.findAll();

        return allProducts
                .stream()
                .map(ProductDTO::fromProduct)
                .toList();
    }

}

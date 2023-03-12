package org.yuri.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuri.productservice.dto.ProductDTO;
import org.yuri.productservice.model.Product;
import org.yuri.productservice.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createProduct(final ProductDTO productDTO) {
        final Product product = ProductDTO.toProduct(productDTO);
        this.productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        final List<Product> products = this.productRepository.findAll();
        final List<ProductDTO> productDTOList = products
                .stream()
                .map(ProductDTO::mapToProductDTO)
                .collect(Collectors.toList());

        return productDTOList;
    }
}

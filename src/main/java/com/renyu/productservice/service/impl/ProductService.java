package com.renyu.productservice.service.impl;

import com.renyu.productservice.dto.CreateProductDTO;
import com.renyu.productservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(final CreateProductDTO productDTO);

    ProductDTO getProduct(final String productId);

    List<ProductDTO> getAllProducts();
}

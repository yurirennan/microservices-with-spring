package org.yuri.productservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yuri.productservice.dto.ProductDTO;
import org.yuri.productservice.service.ProductService;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody final ProductDTO productDTO) {
        this.productService.createProduct(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts() {
        final List<ProductDTO> products = this.productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}

package com.renyu.productservice.controller;

import com.renyu.productservice.dto.CreateProductDTO;
import com.renyu.productservice.dto.ProductDTO;
import com.renyu.productservice.service.impl.ProductService;
import com.renyu.productservice.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid CreateProductDTO createProductDTO ) {

        this.productService.createProduct(createProductDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") final String productId) {
        final ProductDTO productDTO = this.productService.getProduct(productId);

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        final List<ProductDTO> products = this.productService.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}

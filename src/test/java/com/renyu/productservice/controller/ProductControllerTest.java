package com.renyu.productservice.controller;

import com.renyu.productservice.IntegrationTestInitializer;
import com.renyu.productservice.dto.CreateProductDTO;
import com.renyu.productservice.dto.ProductDTO;
import com.renyu.productservice.exceptions.ApiError;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductControllerTest extends IntegrationTestInitializer {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createProduct() {
        final String resourceLocation = "/api/products/";

        final CreateProductDTO productDTO = this.getProductDTO();

        final HttpEntity<CreateProductDTO> httpEntity = new HttpEntity<>(productDTO);

        final ResponseEntity<Void> response = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.POST,
                        httpEntity,
                        Void.class
                );

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deveSerPossivelListarUmProduto() {
        final String resourceLocation = "/api/products/";

        final CreateProductDTO productDTO = this.getProductDTO();

        final HttpEntity<CreateProductDTO> httpEntity = new HttpEntity<>(productDTO);

        final ResponseEntity<ProductDTO> response = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.POST,
                        httpEntity,
                        ProductDTO.class
                );

        assertNotNull(response);

        final ProductDTO productExpected = response.getBody();
        assertNotNull(productExpected);

        final String productId = productExpected.getId();

        final ResponseEntity<ProductDTO> productDTOResponseEntity = this.testRestTemplate
                .exchange(
                        resourceLocation + productId,
                        HttpMethod.GET,
                        httpEntity,
                        ProductDTO.class
                );

        assertNotNull(productDTOResponseEntity);

        final ProductDTO productActualDTO = productDTOResponseEntity.getBody();
        assertNotNull(productActualDTO);

        assertEquals(productExpected.getId(), productActualDTO.getId());
        assertEquals(productExpected.getName(), productActualDTO.getName());
        assertEquals(productExpected.getDescription(), productActualDTO.getDescription());
        assertEquals(productExpected.getPrice(), productActualDTO.getPrice());
    }

    @Test
    public void naoDeveSerPossivelListarUmProdutoQueNaoExiste() {
        final String resourceLocation = "/api/products/1";

        final HttpEntity<Object> httpEntity = new HttpEntity<>(null);

        final ResponseEntity<ApiError> productDTOResponseEntity = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.GET,
                        httpEntity,
                        ApiError.class
                );

        assertNotNull(productDTOResponseEntity);

        final ApiError apiError = productDTOResponseEntity.getBody();

        assertNotNull(apiError);
        assertEquals("PRODUCT NOT FOUND!", apiError.getErrors().get(0));
    }

    @Test
    public void deveSerPossivelListarTodosOsProdutos() {
        final String resourceLocation = "/api/products/";

        final HttpEntity<Object> httpEntity = new HttpEntity<>(null);

        final ResponseEntity<List<ProductDTO>> response = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<ProductDTO>>() {
                        }
                );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final List<ProductDTO> products = response.getBody();

        assertNotNull(products);
        assertEquals(0, products.size());


        final CreateProductDTO productDTO = this.getProductDTO();

        final HttpEntity<CreateProductDTO> createproductHttpEntity = new HttpEntity<>(productDTO);

        final ResponseEntity<Void> createProductResponse = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.POST,
                        createproductHttpEntity,
                        Void.class
                );

        assertEquals(createProductResponse.getStatusCode(), HttpStatus.OK);

        final ResponseEntity<List<ProductDTO>> listAllProductsResponse = this.testRestTemplate
                .exchange(
                        resourceLocation,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<List<ProductDTO>>() {
                        }
                );

        assertNotNull(listAllProductsResponse);
        assertEquals(HttpStatus.OK, listAllProductsResponse.getStatusCode());

        final List<ProductDTO> allProductsResponseBody = listAllProductsResponse.getBody();

        assertNotNull(allProductsResponseBody);
        assertEquals(1, allProductsResponseBody.size());
    }


    private CreateProductDTO getProductDTO() {
        final CreateProductDTO productDTO = new CreateProductDTO();

        productDTO.setName("Test Name");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(BigDecimal.valueOf(10));

        return productDTO;
    }

}
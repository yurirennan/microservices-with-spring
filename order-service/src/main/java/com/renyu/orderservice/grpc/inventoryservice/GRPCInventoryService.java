package com.renyu.orderservice.grpc.inventoryservice;

import com.renyu.orderservice.grpc.productservice.InventoryServiceGrpc;
import com.renyu.orderservice.grpc.productservice.ProductDTO;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryRequest;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GRPCInventoryService {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;

    @Autowired
    public GRPCInventoryService(final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub) {
        this.inventoryServiceBlockingStub = inventoryServiceBlockingStub;
    }

    @CircuitBreaker(name = "checkIfProductInInventory", fallbackMethod = "fallback")
    public List<ProductDTO> checkIfProductInInventory(final List<String> skuCodes) {
        final ProductInInventoryRequest productInInventoryRequest = ProductInInventoryRequest.newBuilder()
                .addAllSkuCode(skuCodes)
                .build();

        final ProductInInventoryResponse productExistsResponse =
                this.inventoryServiceBlockingStub.productInStock(productInInventoryRequest);

        final List<ProductDTO> products = productExistsResponse.getProductList();

        return products
                .stream()
                .filter(productDTO -> !productDTO.getInStock())
                .collect(Collectors.toList());

    }

    private List<ProductDTO> fallback(final List<String> skuCodes, Throwable e) {
        return skuCodes.stream().map(skuCode -> ProductDTO.newBuilder().setSkuCode(skuCode).setInStock(false).build()).toList();
    }

}

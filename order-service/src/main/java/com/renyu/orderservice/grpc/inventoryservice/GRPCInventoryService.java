package com.renyu.orderservice.grpc.inventoryservice;

import com.renyu.orderservice.grpc.productservice.InventoryServiceGrpc;
import com.renyu.orderservice.grpc.productservice.ProductDTO;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryRequest;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GRPCInventoryService {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;

    @Autowired
    public GRPCInventoryService(final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub) {
        this.inventoryServiceBlockingStub = inventoryServiceBlockingStub;
    }

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

}

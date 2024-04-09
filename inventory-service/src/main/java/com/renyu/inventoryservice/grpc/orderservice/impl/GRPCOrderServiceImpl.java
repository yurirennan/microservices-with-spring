package com.renyu.inventoryservice.grpc.orderservice.impl;

import com.renyu.inventoryservice.model.InventoryItem;
import com.renyu.inventoryservice.repository.InventoryItemRepository;
import com.renyu.orderservice.grpc.productservice.InventoryServiceGrpc;
import com.renyu.orderservice.grpc.productservice.ProductDTO;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryRequest;
import com.renyu.orderservice.grpc.productservice.ProductInInventoryResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class GRPCOrderServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryItemRepository inventoryItemRepository;

    @Autowired
    public GRPCOrderServiceImpl(final InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public void productInStock(ProductInInventoryRequest request, StreamObserver<ProductInInventoryResponse> responseObserver) {
        List<String> skuCodes = request.getSkuCodeList();

        final List<InventoryItem> productsInStock =
                this.inventoryItemRepository.findBySkuCodeAndQuantityGreaterThanZero(skuCodes);

        final List<String> skuCodeFromProductsInStock = productsInStock.stream().map(InventoryItem::getSkuCode).toList();

        final List<String> skuCodeFromProductsOutOfStock = skuCodes
                .stream()
                .filter(skuCode -> !skuCodeFromProductsInStock.contains(skuCode))
                .toList();

        final List<ProductDTO> productsOutOfStock = skuCodeFromProductsOutOfStock
                .stream()
                .map(skuCode -> ProductDTO.newBuilder().setInStock(false).setSkuCode(skuCode).build())
                .toList();

        final ProductInInventoryResponse productInInventoryResponse = ProductInInventoryResponse
                .newBuilder()
                .addAllProduct(productsOutOfStock)
                .build();

        responseObserver.onNext(productInInventoryResponse);
        responseObserver.onCompleted();
    }

}

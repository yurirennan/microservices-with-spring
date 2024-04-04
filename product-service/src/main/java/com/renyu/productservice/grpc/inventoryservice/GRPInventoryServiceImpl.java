package com.renyu.productservice.grpc.inventoryservice;


import com.renyu.inventoryservice.grpc.productservice.ProductExistsRequest;
import com.renyu.inventoryservice.grpc.productservice.ProductExistsResponse;
import com.renyu.inventoryservice.grpc.productservice.ProductServiceGrpc;
import com.renyu.productservice.model.Product;
import com.renyu.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class GRPInventoryServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository productRepository;

    @Autowired
    public GRPInventoryServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void productExists(final ProductExistsRequest request, final StreamObserver<ProductExistsResponse> responseObserver) {
        final String skuCode = request.getSkuCode();

        Optional<Product> productOptional = this.productRepository.findBySkuCode(skuCode);

        final boolean productExists = productOptional.isPresent();

        final ProductExistsResponse response = ProductExistsResponse.newBuilder()
                .setExists(productExists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

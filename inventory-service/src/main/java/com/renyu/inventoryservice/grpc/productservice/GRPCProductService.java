package com.renyu.inventoryservice.grpc.productservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GRPCProductService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    @Autowired
    public GRPCProductService(final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub) {
        this.productServiceBlockingStub = productServiceBlockingStub;
    }

    @CircuitBreaker(name = "checkIfProductExists", fallbackMethod = "fallback")
    public boolean checkIfProductExists(final String skuCode) {
        final ProductExistsRequest productExistsRequest = ProductExistsRequest.newBuilder()
                .setSkuCode(skuCode)
                .build();

        final ProductExistsResponse productExistsResponse =
                this.productServiceBlockingStub.productExists(productExistsRequest);

        return productExistsResponse.getExists();
    }

    private boolean fallback(final String skuCode, Throwable e) {
        return false;
    }

}

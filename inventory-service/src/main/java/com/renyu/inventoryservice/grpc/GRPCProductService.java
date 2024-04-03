package com.renyu.inventoryservice.grpc;

import com.renyu.inventoryservice.grpc.productservice.ProductExistsRequest;
import com.renyu.inventoryservice.grpc.productservice.ProductExistsResponse;
import com.renyu.inventoryservice.grpc.productservice.ProductServiceGrpc;
import com.renyu.inventoryservice.grpc.provider.ProductServiceIntegrationGRPCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GRPCProductService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    @Autowired
    public GRPCProductService(ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub) {
        this.productServiceBlockingStub = productServiceBlockingStub;
    }

    public boolean checkIfProductExists(final String skuCode) {
        final ProductExistsRequest productExistsRequest = ProductExistsRequest.newBuilder()
                .setSkuCode(skuCode)
                .build();

        final ProductExistsResponse productExistsResponse =
                this.productServiceBlockingStub.productExists(productExistsRequest);

        return productExistsResponse.getExists();
    }

}

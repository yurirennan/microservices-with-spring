package com.renyu.orderservice.grpc.inventoryservice.provider;

import com.renyu.orderservice.grpc.productservice.InventoryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryServiceIntegrationGRPCProvider {
    private final String hostName;
    private final Integer port;

    public InventoryServiceIntegrationGRPCProvider(@Value("${inventory-service.grpc.hostname}") final String hostName,
                                                   @Value("${inventory-service.grpc.port}")Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Bean("inventoryServiceChannel")
    public ManagedChannel provideManagedChannel() {

        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress(this.hostName, this.port)
                .usePlaintext()
                .build();

        return channel;
    }

    @Bean
    public InventoryServiceGrpc.InventoryServiceBlockingStub provideProductServiceStub(@Qualifier("inventoryServiceChannel") ManagedChannel managedChannel) {
        final InventoryServiceGrpc.InventoryServiceBlockingStub stub = InventoryServiceGrpc.newBlockingStub(managedChannel);

        return stub;
    }

}

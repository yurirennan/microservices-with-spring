package com.renyu.inventoryservice.grpc.provider;

import com.renyu.inventoryservice.grpc.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe Responsável por fornecer as configurações para a conexão com o product-service
 * via GRPC.
 *
 * @author Yuri Rennan <yuri.rennan@sogo.com.br>
 */
@Configuration
public class ProductServiceIntegrationGRPCProvider {
    private final String hostName;
    private final Integer port;

    public ProductServiceIntegrationGRPCProvider(@Value("${product-service.grpc.hostname}") final String hostName,
                                                 @Value("${product-service.grpc.port}")Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Bean("productServiceChannel")
    public ManagedChannel provideManagedChannel() {

        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress(this.hostName, this.port)
                .usePlaintext()
                .build();

        return channel;
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub provideProductServiceStub(@Qualifier("productServiceChannel") ManagedChannel managedChannel) {
        final ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(managedChannel);

        return stub;
    }

}

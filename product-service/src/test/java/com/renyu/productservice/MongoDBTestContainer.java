package com.renyu.productservice;

import org.testcontainers.containers.GenericContainer;

public class MongoDBTestContainer extends GenericContainer<MongoDBTestContainer> {

    private static final String IMAGE_VERSION = "mongo:latest";
    private static final int PORT = 27017;

    public MongoDBTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MongoDBTestContainer getInstance() {
        return new MongoDBTestContainer()
                .withExposedPorts(PORT)
                .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", "admin")
                .withEnv("MONGO_INITDB_DATABASE", "product-service");
    }

    @Override
    public void start() {
        super.start();

        final Integer localPort = getMappedPort(PORT);
        System.setProperty("MONGO_DB_PORT", localPort.toString());
    }

    @Override
    public void stop() {}

}

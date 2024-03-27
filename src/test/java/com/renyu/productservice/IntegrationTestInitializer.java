package com.renyu.productservice;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestInitializer {

    @ClassRule
    public static final MongoDBTestContainer MONGO_DB_TEST_CONTAINER = MongoDBTestContainer.getInstance();

    /**
     * Como esta é apenas uma classe de configuração que servirá para ser estendida por outras
     * classes que realmente terão implementações de Testes, é necessário implementar um teste
     * básico com apenas um Assert.assertTrue(true) para que, durante a execução dos testes, o
     * JUnit não retorne o erro `{java.lang.Exception: No runnable methods}' para esta classe.
     */
    @Test
    public void simpleAssertIntegrationTestInitializer() {
        Assert.assertTrue(true);
    }
}

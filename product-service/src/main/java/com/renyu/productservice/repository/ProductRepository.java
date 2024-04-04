package com.renyu.productservice.repository;

import com.renyu.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'name':  ?0 }")
    Optional<Product> findBySkuCode(String skuCode);
}

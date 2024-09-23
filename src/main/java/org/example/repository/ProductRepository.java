package org.example.repository;

import org.example.entity.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("productRepository")
public interface ProductRepository extends GenericRepository<Product, Long> {
}


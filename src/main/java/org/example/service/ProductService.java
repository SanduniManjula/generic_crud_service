package org.example.service;


import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends GenericService<Product, Long> {
    @Autowired
    public ProductService(@Qualifier("productRepository") ProductRepository productRepository) {
        super(productRepository);
    }
}

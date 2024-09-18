package org.example.controller;

import org.example.entity.Product;
import org.example.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController extends CrudController<Product, Long> {

    public ProductController(CrudService<Product, Long> service) {
        super(service);
    }
}

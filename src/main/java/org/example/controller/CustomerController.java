package org.example.controller;

import org.example.entity.Customer;
import org.example.service.CrudService;
import org.example.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController extends CrudController<Customer, Long> {

    public CustomerController(CrudService<Customer, Long> service) {
        super(service);
    }
}

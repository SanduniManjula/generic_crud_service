package org.example.service;

import org.example.entity.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends GenericService<Customer, Long> {
    @Autowired
    public CustomerService(@Qualifier("customerRepository") CustomerRepository customerRepository) {
        super(customerRepository);
    }
}

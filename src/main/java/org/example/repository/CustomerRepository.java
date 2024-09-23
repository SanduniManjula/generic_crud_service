package org.example.repository;
import org.example.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("customerRepository")
public interface CustomerRepository extends GenericRepository<Customer, Long> {
}

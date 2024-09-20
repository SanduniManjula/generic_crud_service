package org.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T, ID> {

    T save(T entity);

    T findById(ID id);

    T update(ID id, T entity);

    void deleteById(ID id);

    Page<T> findAll(Pageable pageable);

    Page<T> searchByField(String fieldName, String value, Pageable pageable);

    Page<T> searchByFieldWithSorting(String fieldName, String value, String sortBy, String sortDirection, Pageable pageable);

}

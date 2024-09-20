package org.example.service;

import org.example.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class GenericService<T, ID> implements CrudService<T, ID> {

    private final GenericRepository<T, ID> repository;

    @Autowired
    public GenericService(@Qualifier("customerRepository") GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public T update(ID id, T entity) {
        if (repository.existsById(id)) {
            return repository.save(entity);
        }
        return null;
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> searchByField(String fieldName, String value, Pageable pageable) {
        Specification<T> specification = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.equal(root.get(fieldName), value);
            return predicate;
        };
        return repository.findAll(specification, pageable);
    }

    @Override
    public Page<T> searchByFieldWithSorting(String fieldName, String value, String sortBy, String sortDirection, Pageable pageable) {
        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return searchByField(fieldName, value, sortedPageable);
    }
}

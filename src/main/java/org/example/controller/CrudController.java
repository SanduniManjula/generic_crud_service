package org.example.controller;

import org.example.exception.ResourceNotFoundException;
import org.example.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public abstract class CrudController<T, ID> {

    private final CrudService<T, ID> service;

    @Autowired
    public CrudController(CrudService<T, ID> service) {
        this.service = service;
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable ID id) {
        return Optional.ofNullable(service.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) {
        return Optional.ofNullable(service.update(id, entity))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        service.deleteById(id);
    }

    @GetMapping
    public Page<T> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<T> search(
            @RequestParam String fieldName,
            @RequestParam String value,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            Pageable pageable) {
        return service.searchByFieldWithSorting(fieldName, value, sortBy, sortDirection, pageable);
    }
}

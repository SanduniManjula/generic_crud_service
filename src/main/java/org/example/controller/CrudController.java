package org.example.controller;

import org.example.exception.ResourceNotFoundException;
import org.example.response.ApiResponse;
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
    public ApiResponse<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return new ApiResponse<>(true, "Entity created successfully", savedEntity);
    }
    @GetMapping("/{id}")
    public ApiResponse<T> getById(@PathVariable ID id) {
        T entity = Optional.ofNullable(service.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ApiResponse<>(true, "Entity retrieved successfully", entity);
    }

    @PutMapping("/{id}")
    public ApiResponse<T> update(@PathVariable ID id, @RequestBody T entity) {
        T updatedEntity = Optional.ofNullable(service.update(id, entity))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ApiResponse<>(true, "Entity updated successfully", updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable ID id) {
        service.deleteById(id);
        return new ApiResponse<>(true, "Entity deleted successfully", null);
    }
    @GetMapping
    public ApiResponse<Page<T>> findAll(Pageable pageable) {
        Page<T> entities = service.findAll(pageable);
        return new ApiResponse<>(true, "Entities retrieved successfully", entities);
    }

    @GetMapping("/search")
    public ApiResponse<Page<T>> search(
            @RequestParam String fieldName,
            @RequestParam String value,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            Pageable pageable) {
        Page<T> entities = service.searchByFieldWithSorting(fieldName, value, sortBy, sortDirection, pageable);
        return new ApiResponse<>(true, "Search results retrieved successfully", entities);
    }
}

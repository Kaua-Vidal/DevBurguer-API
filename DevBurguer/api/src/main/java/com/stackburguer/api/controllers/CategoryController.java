package com.stackburguer.api.controllers;

import com.stackburguer.api.DTO.CategoryRequestDTO;
import com.stackburguer.api.DTO.CategoryResponseDTO;
import com.stackburguer.api.models.Category;
import com.stackburguer.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request){
        Category savedCategory = categoryService.createCategory(request);

        CategoryResponseDTO response = new CategoryResponseDTO(
                savedCategory.getId(),
                savedCategory.getName()
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable String id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }



}

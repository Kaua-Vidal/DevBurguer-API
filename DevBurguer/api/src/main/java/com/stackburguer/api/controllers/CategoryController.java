package com.stackburguer.api.controllers;

import com.stackburguer.api.models.Category;
import com.stackburguer.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> ListAll(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public Category save(@RequestBody Category category){
        return categoryService.createCategory(category);
    }


}

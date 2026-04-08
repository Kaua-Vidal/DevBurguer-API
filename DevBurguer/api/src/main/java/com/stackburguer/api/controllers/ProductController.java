package com.stackburguer.api.controllers;


import com.stackburguer.api.models.Product;
import com.stackburguer.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")  //todas minhas rotas começam com /products
public class ProductController {

    private ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> listAll(){
        return service.getAllProducts();
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return service.createProduct(product);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategoryId(@PathVariable String categoryId){
        return service.getProductsByCategory(categoryId);
    }
}

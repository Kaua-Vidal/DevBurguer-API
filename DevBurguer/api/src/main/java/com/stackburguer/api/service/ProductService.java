package com.stackburguer.api.service;

import com.stackburguer.api.models.Product;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String categoryId){
        return productRepository.findByCategoryId(categoryId);
    }
}

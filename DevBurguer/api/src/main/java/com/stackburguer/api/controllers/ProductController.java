package com.stackburguer.api.controllers;


import com.stackburguer.api.DTO.ProductRequestDTO;
import com.stackburguer.api.DTO.ProductResponseDTO;
import com.stackburguer.api.models.Product;
import com.stackburguer.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")  //todas minhas rotas começam com /products
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponseDTO> getAll(){
        List<Product> products = service.getAllProducts();

        return products.stream().map(product -> {
            String fullUrl = "http://localhost:8080/uploads/" + product.getPath();

            return new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    fullUrl,
                    product.getCategoryId()
            );
        }).toList();

    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductResponseDTO> create(
            @RequestPart("product") String productJson,
            @RequestPart("file") MultipartFile file
    ) throws IOException{

        //ObjectMapper é o cara que entende JSON no Spring
        ObjectMapper objectMapper = new ObjectMapper();

        // Pegamos a String e encaixamos na classe product
        ProductRequestDTO requestDto = objectMapper.readValue(productJson, ProductRequestDTO.class);
        ProductResponseDTO response = service.createProduct(requestDto, file);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategoryId(@PathVariable String categoryId){
        return service.getProductsByCategory(categoryId);
    }


    @DeleteMapping("/{id}")  //Serve para quando for chamar o delete: /products/5, o 5 já vai ser o ID
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestParam("product") String productJson,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product productDetails = objectMapper.readValue(productJson, Product.class);

        return ResponseEntity.ok(service.updateProduct(id, productDetails, file));
    }
}

package com.stackburguer.api.controllers;


import com.stackburguer.api.DTO.ProductRequestDTO;
import com.stackburguer.api.DTO.ProductResponseDTO;
import com.stackburguer.api.DTO.StandardError;
import com.stackburguer.api.models.Product;
import com.stackburguer.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            List<ProductResponseDTO> products = service.getAllProducts();
            return ResponseEntity.ok(products);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar produtos.");
        }


    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductResponseDTO> create(
            @Valid
            @RequestPart("product") String productJson,
            @RequestPart("file") MultipartFile file
    ) throws IOException{
            ProductResponseDTO response = service.createProduct(productJson, file);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.id())
                    .toUri();
            return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategoryId(@PathVariable String categoryId){
        List<ProductResponseDTO> products = service.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }


    @DeleteMapping("/{id}")  //Serve para quando for chamar o delete: /products/5, o 5 já vai ser o ID
    public ResponseEntity<Void> delete(@PathVariable Long id) throws RuntimeException{
            //O Service realiza a exclusão
            service.deleteProduct(id);

            // No sucesso, retornamos o status 204
            // o .build() cria um ResponseEntity sem corpo
            return ResponseEntity.noContent().build();  //204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestParam("product") String productJson,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
            ProductResponseDTO response = service.updateProduct(id, productJson, file);
            return ResponseEntity.ok(response);
    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ProductRequestDTO productDetails = objectMapper.readValue(productJson, ProductRequestDTO.class);
//
//        ProductResponseDTO response = service.updateProduct(id, productDetails, file);
//
//        URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequest()  //onde estoua aogra?
//                .path("/{id}")  // o quero adicionar (ID)?
//                .buildAndExpand(response.id())
//                .toUri();
//
//        return ResponseEntity.created(uri).body(response);
//    }
}

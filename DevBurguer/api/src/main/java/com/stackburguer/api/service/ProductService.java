package com.stackburguer.api.service;

import com.stackburguer.api.DTO.ProductRequestDTO;
import com.stackburguer.api.DTO.ProductResponseDTO;
import com.stackburguer.api.exceptions.CategoryNotFoundException;
import com.stackburguer.api.models.Product;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import com.stackburguer.api.repositories.mongo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public ProductResponseDTO createProduct(String productJson, MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO requestDto = objectMapper.readValue(productJson, ProductRequestDTO.class);

        Product product = new Product();
        product.setName(requestDto.name());
        product.setPrice(requestDto.price());
        product.setCategoryId(requestDto.categoryId());

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + File.separator + "src" + "/main/resources/static/uploads/";
        file.transferTo(new File(uploadDir + fileName));
        product.setPath(fileName);

        Product savedProduct = productRepository.save(product);
        return mapToResponseDTO(savedProduct);

    }
    private ProductResponseDTO mapToResponseDTO(Product product){
        String fullUrl = "http://localhost:8080/uploads/" + product.getPath();
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                fullUrl,
                product.getCategoryId()
        );
    }

    public List<ProductResponseDTO> getProductsByCategory(String categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        String projectPath = System.getProperty("user.dir");
        String uploadDir = projectPath + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "static" +
                File.separator + "uploads" + File.separator;

        File fileToDelete = new File(uploadDir + product.getPath());

        if (fileToDelete.exists()) {
            boolean success = fileToDelete.delete();
            if(success) {
                System.out.println("Arquivo deletado com sucesso: " + product.getPath());
            } else {
                System.out.println("Falha ao deletar o arquivo físico");
            }
        }

        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(Long id, String productJson, MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO dto = objectMapper.readValue(productJson, ProductRequestDTO.class);


        //Pegamos o produto atual que está no banco
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        //Atualização dos textos
        existingProduct.setName(dto.name());
        existingProduct.setPrice(dto.price());
        existingProduct.setCategoryId(dto.categoryId());

        //Validação da categoria, caso mude a categoria, checamos no MONGO
        if(!categoryRepository.existsById(dto.categoryId())) {
            throw new RuntimeException("Nova categoria informada não existe no Mongo");
        }



        if(file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir");
            String uploadDir = projectPath + File.separator + "src" + File.separator + "main" +
                    File.separator + "resources" + File.separator + "static" +
                    File.separator + "uploads" + File.separator;

            File oldFile = new File(uploadDir + existingProduct.getPath());
            if (oldFile.exists()) {
                oldFile.delete(); //Limpando a foto do HD caso mande a foto nova
            }

            //Salvando a foto nova
            String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadDir + newFileName));

            // Atualizando o caminho no objeto com o novo nome
            existingProduct.setPath(newFileName);
        }

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToResponseDTO(updatedProduct);
    }
}

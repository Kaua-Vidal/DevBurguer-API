package com.stackburguer.api.service;

import com.stackburguer.api.DTO.product.ProductRequestDTO;
import com.stackburguer.api.DTO.product.ProductResponseDTO;
import com.stackburguer.api.models.Category;
import com.stackburguer.api.models.Product;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import com.stackburguer.api.repositories.jpa.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final S3Service s3Service;

    public ProductService(S3Service s3Service){
        this.s3Service = s3Service;
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public String saveFile(MultipartFile file) {
        try {
            // 1. Definimos que a pasta alvo é a "uploads" na raiz do projeto
            Path root = Paths.get("uploads");

            // 2. Criamos a pasta caso ela não exista (importante para o primeiro upload)
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            // 3. Geramos o nome do arquivo (pode usar o seu timestamp se quiser)
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destination = root.resolve(fileName);

            // 4. Salvamos o arquivo fisicamente na pasta uploads
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Retornamos apenas o NOME do arquivo para salvar no banco
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo", e);
        }
    }

    public ProductResponseDTO createProduct(String productJson, MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO requestDto = objectMapper.readValue(productJson, ProductRequestDTO.class);

        if(requestDto.price() <= 0){
            throw new RuntimeException("O proço do produto deve ser maior que zero.");
        }

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));


        Product product = new Product();
        product.setName(requestDto.name());
        product.setPrice(requestDto.price());
        product.setCategory(category);
        product.setOffer(requestDto.offer() != null ? requestDto.offer() : false);

        String fileName = saveFile(file);
        product.setPath(fileName);

        Product savedProduct = productRepository.save(product);
        return mapToResponseDTO(savedProduct);

    }
    private ProductResponseDTO mapToResponseDTO(Product product){
        return new ProductResponseDTO(product);
    }

    public List<ProductResponseDTO> getProductsByCategory(String categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);

        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Categoria ID " + categoryId + " não encontrada.");
        }

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

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("A categoria informada não existe no banco"));

        //Atualização dos textos
        existingProduct.setName(dto.name());
        existingProduct.setPrice(dto.price());
        existingProduct.setCategory(category);
        existingProduct.setOffer(dto.offer());




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

    public String updateProductImage(Long productId, MultipartFile file){
        String fileUrl = s3Service.uploadFile(file);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + productId));

        product.setPath(fileUrl);

        productRepository.save(product);
        return fileUrl;
    }
}

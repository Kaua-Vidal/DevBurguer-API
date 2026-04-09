package com.stackburguer.api.service;

import com.stackburguer.api.exceptions.CategoryNotFoundException;
import com.stackburguer.api.models.Product;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import com.stackburguer.api.repositories.mongo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(Product product, MultipartFile file) throws IOException {

        //Validando a categoria recebida
        boolean exists = categoryRepository.existsById(product.getCategoryId());
        if(!exists) {
            throw new CategoryNotFoundException("Erro: A categoria com ID " + product.getCategoryId() + " não existe no mongoDB");
        }

        //Pegamos o caminho absoluto da raiz do projeto (C:\Users\Kauã\Documents\meu-projeto)
        String projectPath = System.getProperty("user.dir");


        // Caminho completo até a pasta de uploads
        String uploadDir = projectPath + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "static" +
                File.separator + "uploads" + File.separator;

        //Caso o arquivo não exista, crie a pasta;
        File directory = new File(uploadDir);
        if(!directory.exists()) directory.mkdirs();

        //Geramos o nome do arquivo
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Pegar o arquivo da pasta temporária e escrever permanentemente
        // na minha pasta de uploads
        file.transferTo(new File(uploadDir + fileName));


        // Salvar o caminho da imagem no objeto produto;
        product.setPath(fileName);

        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String categoryId){
        return productRepository.findByCategoryId(categoryId);
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

    public Product updateProduct(Long id, Product details, MultipartFile file) throws IOException {

        //Pegamos o produto atual que está no banco
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        //Validação da categoria, caso mude a categoria, checamos no MONGO
        if(!categoryRepository.existsById(details.getCategoryId())) {
            throw new RuntimeException("Nova categoria informada não existe no Mongo");
        }

        //Atualização dos textos
        existingProduct.setName(details.getName());
        existingProduct.setCategoryId(details.getCategoryId());
        existingProduct.setPrice(details.getPrice());

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

        return productRepository.save(existingProduct);
    }
}

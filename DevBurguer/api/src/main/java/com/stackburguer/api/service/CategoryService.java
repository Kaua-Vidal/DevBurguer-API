package com.stackburguer.api.service;

import com.stackburguer.api.DTO.category.CategoryRequestDTO;
import com.stackburguer.api.DTO.category.CategoryResponseDTO;
import com.stackburguer.api.models.Category;
import com.stackburguer.api.repositories.mongo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryResponseDTO mapToResponseDTO(Category category){
        String path = category.getPath();

        String url = "http://localhost:8080/category-file/" + path;

        return new CategoryResponseDTO(category.getId(), category.getName(), url, path);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryDTO){
        Category category = new Category();

        category.setName(categoryDTO.name());

        return mapToResponseDTO(categoryRepository.save(category)) ;
    }

    public CategoryResponseDTO update(String id, CategoryRequestDTO dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
        category.setName(dto.name());
        return mapToResponseDTO(categoryRepository.save(category));
    }

    public void delete(String id) {
        if (!categoryRepository.existsById(id)){
            throw new RuntimeException("Categoria não encontrada.");
        }
        categoryRepository.deleteById(id);
    }
}

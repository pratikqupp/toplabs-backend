package com.TopLabBazaar2909.TLBnew2909.serviceImpl;


import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.CategoryService;
import com.TopLabBazaar2909.TLBnew2909.dto.CategoryDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Category;
import com.TopLabBazaar2909.TLBnew2909.exception.CategoryNotFoundException;
import com.TopLabBazaar2909.TLBnew2909.repository.CategoryRepository;
import com.TopLabBazaar2909.TLBnew2909.resource.CategoryResource;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.CategoryResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryResourceAssembler categoryResourceAssembler;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResource createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        //  Generate manual ID
        String lastId = categoryRepository.findTopByOrderByIdDesc()
                .map(Category::getId)
                .orElse("CAT000"); // default if no records exist
        int nextIdNum = Integer.parseInt(lastId.replace("CAT", "")) + 1;
        String nextId = String.format("CAT%03d", nextIdNum); // e.g., CAT001, CAT002
        category.setId(nextId);

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return categoryResourceAssembler.toModel(savedCategory);
    }



    @Override
    public CategoryResource updateCategory(String id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updated = categoryRepository.save(category);
        return categoryResourceAssembler.toModel(updated);
    }


    @Override
    public void deleteCategory(String id) {
       categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResource> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        // Convert each Category to CategoryResource using toModel()
        return categories.stream()
                .map(categoryResourceAssembler::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResource getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return categoryResourceAssembler.toModel(category);
    }

}

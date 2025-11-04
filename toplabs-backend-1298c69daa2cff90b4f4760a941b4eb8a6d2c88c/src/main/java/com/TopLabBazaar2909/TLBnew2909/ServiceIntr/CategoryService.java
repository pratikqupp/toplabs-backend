package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

//import com.qup.microservices.provider.laboratory.dto.CategoryDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.CategoryDTO;
import com.TopLabBazaar2909.TLBnew2909.resource.CategoryResource;

import java.util.List;

public interface CategoryService {

    CategoryResource updateCategory(String id, CategoryDTO categoryDTO);


    CategoryResource createCategory(CategoryDTO categoryDTO);

    void deleteCategory(String id);

    List<CategoryResource> getAllCategories();

    CategoryResource getCategoryById(String id);
}

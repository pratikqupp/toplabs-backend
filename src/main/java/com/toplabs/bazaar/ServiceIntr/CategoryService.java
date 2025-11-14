package com.toplabs.bazaar.ServiceIntr;

//import com.qup.microservices.provider.laboratory.dto.CategoryDTO;
import com.toplabs.bazaar.dto.CategoryDTO;
import com.toplabs.bazaar.resource.CategoryResource;

import java.util.List;

public interface CategoryService {

    CategoryResource updateCategory(String id, CategoryDTO categoryDTO);


    CategoryResource createCategory(CategoryDTO categoryDTO);

    void deleteCategory(String id);

    List<CategoryResource> getAllCategories();

    CategoryResource getCategoryById(String id);
}

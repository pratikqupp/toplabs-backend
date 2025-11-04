package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;


import com.TopLabBazaar2909.TLBnew2909.controller.PackageController;
import com.TopLabBazaar2909.TLBnew2909.entity.Category;
import com.TopLabBazaar2909.TLBnew2909.resource.CategoryResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoryResourceAssembler implements RepresentationModelAssembler<Category, CategoryResource> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        PropertyMap<Category, CategoryResource> serviceMap = new PropertyMap<Category, CategoryResource>() {
            @Override
            protected void configure() {
                map().setCategoryId(String.valueOf(source.getId()));
            }
        };
        modelMapper.addMappings(serviceMap);
    }

    @Override
    public CategoryResource toModel(Category entity) {
        CategoryResource resource = modelMapper.map(entity, CategoryResource.class);

        // Optional: Add self link
        resource.add(linkTo(methodOn(PackageController.class)
                .getCategoryById(String.valueOf(entity.getId()))).withSelfRel());

        return resource;
    }
}

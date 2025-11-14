package com.toplabs.bazaar.resource;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CategoryResource extends RepresentationModel<CategoryResource> {
    private String categoryId;
    private String name;
    private String description;
}

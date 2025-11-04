package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;

import com.TopLabBazaar2909.TLBnew2909.controller.TestController;
import com.TopLabBazaar2909.TLBnew2909.entity.Test;
import com.TopLabBazaar2909.TLBnew2909.resource.TestResource;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailsTestResourceAssembler
        extends RepresentationModelAssemblerSupport<Test, TestResource> {

    private final ModelMapper modelMapper;

    @Autowired
    public DetailsTestResourceAssembler(ModelMapper modelMapper) {
        super(TestController.class, TestResource.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        PropertyMap<Test, TestResource> serviceMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setTestId(String.valueOf(source.getId()));
            }
        };
        modelMapper.addMappings(serviceMap);
    }

    @Override
    public TestResource toModel(Test entity) {
        // Convert entity to resource
        TestResource resource = modelMapper.map(entity, TestResource.class);

        // (Optional) Add HATEOAS link if your controller has a method for it
        // Example:
        // resource.add(linkTo(methodOn(TestController.class).getTestById(entity.getId())).withSelfRel());

        return resource;
    }

    // ✅ Helper for converting List<Test> → List<TestResource>
    public List<TestResource> toModelList(List<Test> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}

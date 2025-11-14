package com.toplabs.bazaar.resourceAssembler;

import com.toplabs.bazaar.controller.PackageController;
import com.toplabs.bazaar.entity.TestProfile;
import com.toplabs.bazaar.resource.TestProfileResource;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TestProfileResourceAssembler implements RepresentationModelAssembler<TestProfile, TestProfileResource> {

    private final ModelMapper modelMapper;

    @Autowired
    public TestProfileResourceAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        PropertyMap<TestProfile, TestProfileResource> serviceMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setTestProfileId(source.getId());
            }
        };
        modelMapper.addMappings(serviceMap);
    }

    @Override
    public TestProfileResource toModel(TestProfile entity) {
        TestProfileResource resource = modelMapper.map(entity, TestProfileResource.class);


        resource.add(linkTo(methodOn(PackageController.class)
                .getClass()) // You can replace with a real endpoint later
                .withSelfRel());

        return resource;
    }
}

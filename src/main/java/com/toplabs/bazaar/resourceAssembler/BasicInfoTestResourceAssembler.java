package com.toplabs.bazaar.resourceAssembler;


import com.toplabs.bazaar.entity.Test;
import com.toplabs.bazaar.resource.BasicInfoTestResource;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class BasicInfoTestResourceAssembler implements RepresentationModelAssembler<Test, BasicInfoTestResource> {

    @Override
    public BasicInfoTestResource toModel(Test test) {
        BasicInfoTestResource resource = new BasicInfoTestResource();
        resource.setTestId(String.valueOf(test.getId()));
        resource.setName(test.getName());
        resource.setDescription(test.getDescription());
        resource.setFavorite(test.getFavorite());
        resource.setSampleType(test.getSampleType());
        resource.setSpecimen(test.getSpecimen());
        resource.setNote(test.getNote());
        resource.setSampleTubeType(test.getSampleTubeType());
        return resource;
    }
}
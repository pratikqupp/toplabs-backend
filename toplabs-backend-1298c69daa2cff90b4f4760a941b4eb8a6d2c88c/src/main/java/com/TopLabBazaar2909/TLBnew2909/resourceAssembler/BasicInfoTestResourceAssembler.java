package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;


import com.TopLabBazaar2909.TLBnew2909.entity.Test;
import com.TopLabBazaar2909.TLBnew2909.resource.BasicInfoTestResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;


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
package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;

import com.TopLabBazaar2909.TLBnew2909.entity.Lab;
import com.TopLabBazaar2909.TLBnew2909.resource.LabResource;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailedLabResourceAssembler implements RepresentationModelAssembler<Lab, LabResource> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.typeMap(Lab.class, LabResource.class).addMappings(mapper -> {
            mapper.map(Lab::getId, LabResource::setLabId);
        });
    }

    @Override
    public LabResource toModel(Lab entity) {
        if (entity.getTestPrices() != null) entity.getTestPrices().size(); // initialize lazy list
        return modelMapper.map(entity, LabResource.class);
    }

    public List<LabResource> toModelList(List<Lab> labs) {
        return labs.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}

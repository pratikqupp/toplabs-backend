package com.toplabs.bazaar.resourceAssembler;

import com.toplabs.bazaar.controller.CampController;
import com.toplabs.bazaar.entity.CampTestMapping;
import com.toplabs.bazaar.resource.CampTestMappingResource;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampTestMappingResourceAssembler
        extends RepresentationModelAssemblerSupport<CampTestMapping, CampTestMappingResource> {

    private final ModelMapper modelMapper;

    @Autowired
    public CampTestMappingResourceAssembler(ModelMapper modelMapper) {
        super(CampController.class, CampTestMappingResource.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        PropertyMap<CampTestMapping, CampTestMappingResource> serviceMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                // ensure the resource's campTestMappingId is populated from the entity id
                map().setCampTestMappingId(String.valueOf(source.getId()));
            }
        };
        modelMapper.addMappings(serviceMap);
    }

    @Override
    public CampTestMappingResource toModel(CampTestMapping entity) {
        // map entity -> resource (ModelMapper will apply the PropertyMap above)
        CampTestMappingResource resource = modelMapper.map(entity, CampTestMappingResource.class);

        return resource;
    }

    /**
     * Utility to convert a list of entities into a list of resource models.
     * Similar to the old toResources() helper from earlier HATEOAS helpers.
     */
    public List<CampTestMappingResource> toModelList(List<CampTestMapping> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}

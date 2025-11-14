package com.toplabs.bazaar.resourceAssembler;



import com.toplabs.bazaar.entity.Camp;
import com.toplabs.bazaar.resource.CampResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class DetailedCampResourceAssembler implements RepresentationModelAssembler<Camp, CampResource> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        PropertyMap<Camp, CampResource> serviceMap = new PropertyMap<Camp, CampResource>() {
            @Override
            protected void configure() {
                map().setCampId(String.valueOf(source.getId()));
            }
        };
        modelMapper.addMappings(serviceMap);
    }

    @Override
    public CampResource toModel(Camp entity) {
        CampResource resource = modelMapper.map(entity, CampResource.class);
        return resource;
    }

}

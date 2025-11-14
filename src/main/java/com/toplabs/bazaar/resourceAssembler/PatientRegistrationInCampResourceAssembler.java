package com.toplabs.bazaar.resourceAssembler;

import com.toplabs.bazaar.entity.PatientCampRegistration;
import com.toplabs.bazaar.resource.PatientRegistrationResourceInCamp;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class PatientRegistrationInCampResourceAssembler
        implements RepresentationModelAssembler<PatientCampRegistration, PatientRegistrationResourceInCamp> {

    @Autowired
    private ModelMapper modelMapper;



    @PostConstruct
    public void init() {
        PropertyMap<PatientCampRegistration, PatientRegistrationResourceInCamp> serviceMap =
                new PropertyMap<PatientCampRegistration, PatientRegistrationResourceInCamp>() {
                    @Override
                    protected void configure() {
                        map().setPatientCampRegistrationId(source.getId());
                    }
                };

        modelMapper.addMappings(serviceMap);
    }

    @Override
    public PatientRegistrationResourceInCamp toModel(PatientCampRegistration entity) {
        // Convert entity to resource
        PatientRegistrationResourceInCamp resource = modelMapper.map(entity, PatientRegistrationResourceInCamp.class);

        // Optionally, add HATEOAS links here if needed
        // Example: resource.add(linkTo(methodOn(CampController.class).getCampById(entity.getCampId())).withSelfRel());

        return resource;
    }


}

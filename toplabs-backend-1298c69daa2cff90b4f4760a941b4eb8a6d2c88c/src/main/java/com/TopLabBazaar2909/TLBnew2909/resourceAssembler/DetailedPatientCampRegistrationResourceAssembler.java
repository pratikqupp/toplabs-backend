package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;

import com.TopLabBazaar2909.TLBnew2909.controller.CampController;
import com.TopLabBazaar2909.TLBnew2909.embedded.AssignedCampUser;
import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;
import com.TopLabBazaar2909.TLBnew2909.resource.DetailedPatientCampRegistrationResource;
import com.TopLabBazaar2909.TLBnew2909.dto.AssignedCampUserDTO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailedPatientCampRegistrationResourceAssembler
        extends RepresentationModelAssemblerSupport<PatientCampRegistration, DetailedPatientCampRegistrationResource> {

    private final ModelMapper modelMapper;

    @Autowired
    public DetailedPatientCampRegistrationResourceAssembler(ModelMapper modelMapper) {
        super(CampController.class, DetailedPatientCampRegistrationResource.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        // Map PatientCampRegistration → DetailedPatientCampRegistrationResource
        PropertyMap<PatientCampRegistration, DetailedPatientCampRegistrationResource> propertyMap =
                new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        map().setPatientCampRegistrationId(source.getId());
                    }
                };
        modelMapper.addMappings(propertyMap);
    }

    @Override
    public DetailedPatientCampRegistrationResource toModel(PatientCampRegistration entity) {
        // Map main entity → resource
        DetailedPatientCampRegistrationResource resource =
                modelMapper.map(entity, DetailedPatientCampRegistrationResource.class);

        // Copy assignedCampUsers list directly (no DTO needed)
        if (entity.getAssignedCampUsers() != null && !entity.getAssignedCampUsers().isEmpty()) {
            resource.setAssignedCampUsers(entity.getAssignedCampUsers());
        } else {
            resource.setAssignedCampUsers(List.of()); // empty list if none
        }

        return resource;
    }

    // Helper to convert list of entities → list of resources
    public List<DetailedPatientCampRegistrationResource> toModelList(List<PatientCampRegistration> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}

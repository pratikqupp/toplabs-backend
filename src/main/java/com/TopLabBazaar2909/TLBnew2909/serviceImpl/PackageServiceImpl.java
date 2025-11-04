package com.TopLabBazaar2909.TLBnew2909.serviceImpl;


import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.PackageService;
import com.TopLabBazaar2909.TLBnew2909.dto.LabPackageDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.SearchRecordDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.TestProfileMappingRequest;
import com.TopLabBazaar2909.TLBnew2909.exception.PackageNotFoundException;
import com.TopLabBazaar2909.TLBnew2909.repository.PackageRepository;
import com.TopLabBazaar2909.TLBnew2909.resource.PackageResource;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.PackageResourceAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.TopLabBazaar2909.TLBnew2909.entity.LabPackage;

import java.util.*;
import java.util.stream.Collectors;

//import java.util.*;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private PackageResourceAssembler packageResourceAssembler;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PackageResource createPackage(LabPackageDTO packageDTO) {
        // Map DTO to entity
        LabPackage packageEntity = modelMapper.map(packageDTO, LabPackage.class);

        // Generate manual ID (e.g., PKG001, PKG002, PKG003...)
        String lastId = packageRepository.findTopByOrderByIdDesc()
                .map(LabPackage::getId)
                .orElse("PKG000"); // default if none exist

        int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
        String newId = String.format("PKG%03d", nextNumber);

        packageEntity.setId(newId);

        // Save entity
        LabPackage savedEntity = packageRepository.save(packageEntity);

        // Convert to resource and return
        return packageResourceAssembler.toModel(savedEntity);
    }





    @Override
    public List<PackageResource> getLabWisePackage(String labId) {
        List<LabPackage> packageList = packageRepository.findByLabIdOrderByNameAsc(labId);

        // Map each LabPackage entity to PackageResource using toModel()
        return packageList.stream()
                .map(packageResourceAssembler::toModel)
                .collect(Collectors.toList());
    }



    @Override
    public List<PackageResource> searchPackageByName(SearchRecordDTO searchRecordDTO) {
        List<LabPackage> packageList = packageRepository.findByNameContainingIgnoreCase(searchRecordDTO.getPhares());

        return packageList.stream()
                .map(packageResourceAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public PackageResource getPackageById(String packageId) {
        LabPackage testPackage = packageRepository.findById(packageId).orElseThrow(
                () -> new PackageNotFoundException("Package not found exception")
        );
        return packageResourceAssembler.toModel(testPackage);
    }


    @Override
    public PackageResource mapTestAndProfiles(String packageId, TestProfileMappingRequest request) {
        LabPackage pkg = packageRepository.findById(packageId).orElseThrow(
                () -> new PackageNotFoundException("Package not found exception")
        );

        // Map testIds
        List<String> existingTestIds = Optional.ofNullable(pkg.getTestIds()).orElse(new ArrayList<>());
        Set<String> testIdSet = new HashSet<>(existingTestIds);
        if (request.getTestIds() != null) {
            testIdSet.addAll(request.getTestIds());
        }
        pkg.setTestIds(new ArrayList<>(testIdSet));

        // Map profileIds
        List<String> existingProfileIds = Optional.ofNullable(pkg.getProfileIds()).orElse(new ArrayList<>());
        Set<String> profileIdSet = new HashSet<>(existingProfileIds);
        if (request.getProfileIds() != null) {
            profileIdSet.addAll(request.getProfileIds());
        }
        pkg.setProfileIds(new ArrayList<>(profileIdSet));

        packageRepository.save(pkg);
        return packageResourceAssembler.toModel(pkg); // <-- updated
    }


    @Override
    public PackageResource unmapTestAndProfiles(String packageId, TestProfileMappingRequest request) {
        LabPackage pkg = packageRepository.findById(packageId).orElseThrow(
                () -> new PackageNotFoundException("Package not found exception")
        );

        if (request.getTestIds() != null && pkg.getTestIds() != null) {
            pkg.getTestIds().removeAll(request.getTestIds());
        }

        if (request.getProfileIds() != null && pkg.getProfileIds() != null) {
            pkg.getProfileIds().removeAll(request.getProfileIds());
        }

        packageRepository.save(pkg);
        return packageResourceAssembler.toModel(pkg); // <-- use toModel()
    }


    @Override
    public Page<LabPackage> getPaginatedPackages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));
        return packageRepository.findAll(pageable);
    }

    @Override
    public List<PackageResource> getPackageByCategoryId(String categoryId) {
        List<LabPackage> packages = packageRepository.findByCategoryId(categoryId);

        // Convert each entity to a resource using toModel()
        return packages.stream()
                .map(packageResourceAssembler::toModel) // convert each Package to PackageResource
                .collect(Collectors.toList());
    }


    @Override
    public List<PackageResource> getAllPackages() {
        List<LabPackage> packages = packageRepository.findAll();

        // Convert each LabPackage to PackageResource
        return packages.stream()
                .map(packageResourceAssembler::toModel) // use toModel() for each item
                .collect(Collectors.toList());
    }


    @Override
    public List<PackageResource> getPackageByLabAndCategory(String labId, String categoryId) {
        List<LabPackage> packages = packageRepository.findByLabIdAndCategoryId(labId, categoryId);

        // Convert each LabPackage entity to PackageResource
        return packages.stream()
                .map(packageResourceAssembler::toModel)
                .collect(Collectors.toList());
    }



}

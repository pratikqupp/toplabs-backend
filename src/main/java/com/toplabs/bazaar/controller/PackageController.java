package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.CategoryService;
import com.toplabs.bazaar.ServiceIntr.PackageService;
import com.toplabs.bazaar.ServiceIntr.ProfileService;
import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.embedded.MappedTest;
import com.toplabs.bazaar.entity.LabPackage;
import com.toplabs.bazaar.entity.TestProfile;
import com.toplabs.bazaar.resource.CategoryResource;
import com.toplabs.bazaar.resource.PackageResource;
import com.toplabs.bazaar.resource.TestProfileResource;
import com.toplabs.bazaar.resourceAssembler.PackageResourceAssembler;
import com.toplabs.bazaar.resourceAssembler.TestProfileResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
@Tag(name = "Package APIs", description = "APIs related to package management")
public class PackageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private TestProfileResourceAssembler testProfileResourceAssembler;

    @Autowired
    private PackageResourceAssembler packageResourceAssembler;

    // CATEGORY APIS

    @PostMapping("/category/register")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Register a new category", description = "Accessible to all authenticated users")
    public CategoryResource createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @GetMapping("/category/{id}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a category by ID", description = "Fetch a specific test category")
    public CategoryResource getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/category/all")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    public List<CategoryResource> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/category/update/{id}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a category", description = "Update category name or description")
    public CategoryResource updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/category/delete/{id}")
   //@PreAuthorize("isAuthenticated()")
    @Transactional
    @Operation(summary = "Delete a category", description = "Deletes the category and associated data if any")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

    // PROFILE APIS

    @PostMapping("/create/test/profile")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a test profile", description = "Accessible to all authenticated users")
    public TestProfileResource createProfile(@RequestBody RegisterProfileDTO registerProfileDTO) {
        return profileService.createProfile(registerProfileDTO);
    }

    @GetMapping("/get/test/profile/{testProfileId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a test profile by ID", description = "Accessible to all authenticated users")
    public TestProfileResource getTestProfileById(@PathVariable String testProfileId) {
        return profileService.getProfileById(testProfileId);
    }

    @GetMapping("/get/lab/wise/test/profiles/{labId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get test profiles by lab", description = "Accessible to all authenticated users")
    public List<TestProfileResource> getLabWiseProfiles(@PathVariable String labId) {
        return profileService.getProfilesByLabId(labId);
    }

    @GetMapping("/get/test/profile/pagination")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get paginated test profiles", description = "Accessible to all authenticated users")
    public PagedModel<EntityModel<TestProfile>> getTestProfilesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            PagedResourcesAssembler<TestProfile> pagedResourcesAssembler
    ) {
        Page<TestProfile> profiles = profileService.getPaginatedTestProfile(page, size);
        return pagedResourcesAssembler.toModel(profiles);
    }

    @PostMapping("/search/test/profile")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search test profile by name", description = "Accessible to all authenticated users")
    public List<TestProfileResource> searchTestProfile(@RequestBody SearchRecordDTO searchRecordDTO) {
        return profileService.searchTestProfile(searchRecordDTO);
    }

    @PutMapping("/map/test/to/test/profile/{testProfileId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Map test to test profile", description = "Accessible to all authenticated users")
    public TestProfileResource mapTestToProfile(@PathVariable String testProfileId, @RequestBody MappedTest mappedTest) {
        return profileService.mapTestToProfile(testProfileId, mappedTest);
    }

    @PutMapping("/unmap/test/to/test/profile/{testProfileId}/{testId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Unmap test from test profile", description = "Accessible to all authenticated users")
    public TestProfileResource unmapTestFromProfile(@PathVariable String testProfileId, @PathVariable String testId) {
        return profileService.unmapTestFromProfile(testProfileId, testId);
    }

    @PutMapping("/update/test/profile/basic/details/{testProfileId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update test profile details", description = "Accessible to all authenticated users")
    public TestProfileResource updateTestProfile(@PathVariable String testProfileId, @RequestBody UpdateProfileDTO updateProfileDTO) {
        return profileService.updateProfile(testProfileId, updateProfileDTO);
    }

    // PACKAGE APIS

    @PostMapping("/create/package")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a package", description = "Accessible to all authenticated users")
    public PackageResource createPackage(@RequestBody LabPackageDTO packageDTO) {
        return packageService.createPackage(packageDTO);
    }

    @GetMapping("/get/lab/wise/package/{labId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get packages by lab", description = "Accessible to all authenticated users")
    public List<PackageResource> getLabWisePackage(@PathVariable String labId) {
        return packageService.getLabWisePackage(labId);
    }

    @PostMapping("/search/package")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search package by name", description = "Accessible to all authenticated users")
    public List<PackageResource> searchPackage(@RequestBody SearchRecordDTO searchRecordDTO) {
        return packageService.searchPackageByName(searchRecordDTO);
    }

    @GetMapping("/get/package/by/{packageId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get package by ID", description = "Accessible to all authenticated users")
    public PackageResource getPackageById(@PathVariable String packageId) {
        return packageService.getPackageById(packageId);
    }

    @PostMapping("/map/test/and/profile/{packageId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Map tests and profiles to package", description = "Accessible to all authenticated users")
    public PackageResource mapTestsAndProfiles(@PathVariable String packageId, @RequestBody TestProfileMappingRequest request) {
        return packageService.mapTestAndProfiles(packageId, request);
    }

    @PostMapping("/unmap/test/and/profile/{packageId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Unmap tests and profiles from package", description = "Accessible to all authenticated users")
    public PackageResource unmapTestsAndProfiles(@PathVariable String packageId, @RequestBody TestProfileMappingRequest request) {
        return packageService.unmapTestAndProfiles(packageId, request);
    }

    @GetMapping("/get/all/package/pagination")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get paginated packages", description = "Accessible to all authenticated users")
    public PagedModel<PackageResource> getPackagesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            PagedResourcesAssembler<LabPackage> pagedResourcesAssembler
    ) {
        Page<LabPackage> packages = packageService.getPaginatedPackages(page, size);
        return pagedResourcesAssembler.toModel(packages, packageResourceAssembler);
    }

    @GetMapping("/get/category/wise/package/{categoryId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get packages by category", description = "Accessible to all authenticated users")
    public List<PackageResource> getPackageByCategory(@PathVariable String categoryId) {
        return packageService.getPackageByCategoryId(categoryId);
    }

    @GetMapping("/get/lab/category/wise/package/{labId}/{categoryId}")
   //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get packages by lab and category", description = "Accessible to all authenticated users")
    public List<PackageResource> getPackageByLabAndCategory(@PathVariable String labId, @PathVariable String categoryId) {
        return packageService.getPackageByLabAndCategory(labId, categoryId);
    }
}

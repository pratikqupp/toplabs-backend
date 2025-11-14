package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.LabOperations;
import com.toplabs.bazaar.dto.LabTestMappingDTO;
import com.toplabs.bazaar.dto.RegisterLabDTO;
import com.toplabs.bazaar.dto.UpdateLabTestPriceDTO;
import com.toplabs.bazaar.resource.LabResource;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/lab")
public class LabOperationController {
    @Autowired
    private LabOperations labOperations;

    private static final List<String> LAB_CERTIFICATES =
            Collections.unmodifiableList(Arrays.asList("NABL", "CAP", "ISO"));

    @GetMapping(path = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get lab certificates",
            description = "Accessible to all users"
    )
    public List<String> getLabCertificate() {
        return LAB_CERTIFICATES;
    }

    @PostMapping("/register")
   //@PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Register lab",
            description = "Accessible to all authenticated users"
    )
    public LabResource registerLab(@RequestBody RegisterLabDTO registerLabDTO) {
        return labOperations.registerLab(registerLabDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/test/map/to/lab/{labId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Map test to lab",
            description = "Map test to lab and lab to test; accessible to authenticated users"
    )
    public LabResource mapTestToLab(@PathVariable(value = "labId") String labId,
                                    @RequestBody LabTestMappingDTO labTestMappingDTO) {
        return labOperations.mapTestToLab(labId, labTestMappingDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/unmap/test/{testId}/{labId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Unmap test from lab",
            description = "Accessible to all authenticated users"
    )
    public LabResource unmapTestToLab(@PathVariable(value = "labId") String labId,
                                      @PathVariable(value = "testId") String testId) {
        return labOperations.unmapTestToLab(labId, testId);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/update/lab/test/price/{labId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update lab test price",
            description = "Accessible to all authenticated users"
    )
    public LabResource updateLabTestPrice(@PathVariable(value = "labId") String labId,
                                          @RequestBody UpdateLabTestPriceDTO updateLabTestPriceDTO) {
        return labOperations.updateTestPrice(labId, updateLabTestPriceDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/get/lab/by/{labId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get lab by ID",
            description = "Accessible to all authenticated users"
    )
    public LabResource getLabById(@PathVariable(value = "labId") String labId) {
        return labOperations.getLabById(labId);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/get/all/labs", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all labs",
            description = "Accessible to all authenticated users"
    )
    public List<LabResource> getAllLabs() {
        return labOperations.getAllLabs();
    }

   //@PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/new/register", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Register lab with logo",
            description = "Accessible to all authenticated users"
    )
    public LabResource registerLabWithLogo(@RequestPart(name = "registerLabDTO") RegisterLabDTO registerLabDTO,
                                           @NotBlank @RequestPart(required = true) MultipartFile file) {
        return labOperations.registerLabWithLogo(file, registerLabDTO);
    }

    @RequestMapping(path = "/download/labLogo/{logoS3Path}/file/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(
            summary = "Download lab logo",
            description = "Accessible to any authenticated user; downloads the lab logo from S3"
    )
    public ResponseEntity<InputStreamResource> downloadLabLogo(@PathVariable(value = "logoS3Path") String logoS3Path) throws IOException {
        InputStreamResource isr = new InputStreamResource(this.labOperations.downloadLabLogo(logoS3Path));
        return new ResponseEntity<>(isr, HttpStatus.OK);
    }
}

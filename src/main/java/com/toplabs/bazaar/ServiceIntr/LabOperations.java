package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.LabTestMappingDTO;
import com.toplabs.bazaar.dto.RegisterLabDTO;
import com.toplabs.bazaar.dto.UpdateLabTestPriceDTO;
import com.toplabs.bazaar.resource.LabResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface LabOperations {
    LabResource registerLab(RegisterLabDTO registerLabDTO);

    LabResource mapTestToLab(String labId, LabTestMappingDTO labTestMappingDTO);

    //Update test price WRT lab and test.
    LabResource updateTestPrice(String labId, UpdateLabTestPriceDTO updateLabTestPriceDTO);

    LabResource getLabById(String labId);

    List<LabResource> getAllLabs();

    LabResource unmapTestToLab(String labId, String testId);

    LabResource registerLabWithLogo(MultipartFile file, RegisterLabDTO registerLabDTO);

    InputStream downloadLabLogo(String logoS3Path);
}

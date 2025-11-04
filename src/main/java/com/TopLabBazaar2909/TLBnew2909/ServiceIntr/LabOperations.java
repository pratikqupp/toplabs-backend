package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.LabTestMappingDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.RegisterLabDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.UpdateLabTestPriceDTO;
import com.TopLabBazaar2909.TLBnew2909.resource.LabResource;
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

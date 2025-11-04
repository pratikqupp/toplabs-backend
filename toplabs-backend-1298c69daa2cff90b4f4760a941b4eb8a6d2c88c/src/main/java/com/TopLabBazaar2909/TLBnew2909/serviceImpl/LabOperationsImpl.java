package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.LabOperations;
import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.entity.*;
import com.TopLabBazaar2909.TLBnew2909.exception.*;
import com.TopLabBazaar2909.TLBnew2909.repository.*;
import com.TopLabBazaar2909.TLBnew2909.resource.LabResource;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.DetailedLabResourceAssembler;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LabOperationsImpl implements LabOperations {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private DetailedLabResourceAssembler detailedLabResourceAssembler;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.s3.labLogo}")
    private String labLogo;

    private static final String THUMBNAIL_OUTPUT_FORMAT = "png";

    @Override
    public LabResource registerLab(RegisterLabDTO registerLabDTO) {
        Lab lab = modelMapper.map(registerLabDTO, Lab.class);

        //  Manual ID generation
        long count = labRepository.count() + 1;
        String labId = String.format("LAB%03d", count);
        lab.setId(labId);

        labRepository.save(lab);
        return detailedLabResourceAssembler.toModel(lab);
    }


    @Override
    public LabResource mapTestToLab(String labId, LabTestMappingDTO dto) {
        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new LabNotFoundException("Lab not found."));
        Test test = testRepository.findById(dto.getTestId())
                .orElseThrow(() -> new TestNotFoundException("Test not found."));

        // Map lab info to test
        MapLabToTestDTO mapLabToTestDTO = new MapLabToTestDTO();
        mapLabToTestDTO.setLabId(lab.getId());
        mapLabToTestDTO.setLabName(lab.getLabName());
        mapLabToTestDTO.setB2bPrice(dto.getB2bPrice());
        mapLabToTestDTO.setMrp(dto.getMrp());
        mapLabToTestDTO.setMmdPrice(dto.getMmdPrice());
        mapLabToTestDTO.setLogoS3Path(lab.getLogoS3Path());
        mapLabToTest(test, mapLabToTestDTO);

        // Map test info to lab entity
        lab = mapTestToLabEntity(lab, dto);

        return detailedLabResourceAssembler.toModel(lab);
    }

    @Override
    public LabResource updateTestPrice(String labId, UpdateLabTestPriceDTO dto) {
        Test test = testRepository.findById(dto.getTestId())
                .orElseThrow(() -> new TestNotFoundException("Test not found."));

        if (test.getTestMappedLabs() == null || test.getTestMappedLabs().isEmpty()) {
            throw new TestNotFoundException("This test is not mapped with any labs.");
        }

        TestMappedLab mappedLab = test.getTestMappedLabs().stream()
                .filter(l -> l.getLabId().equals(labId))
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("This test is not mapped with the specified lab."));

        mappedLab.setB2bPrice(dto.getB2bPrice());
        mappedLab.setMrp(dto.getMrp());
        mappedLab.setMmdPrice(dto.getMmdPrice());
        testRepository.save(test);

        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new LabNotFoundException("Lab not found."));

        if (lab.getTestPrices() == null || lab.getTestPrices().isEmpty()) {
            throw new TestNotFoundException("Test does not exist in lab.");
        }

        TestPrice testPrice = lab.getTestPrices().stream()
                .filter(tp -> tp.getTestId().equals(dto.getTestId()))
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("Test does not exist in lab."));

        testPrice.setB2bPrice(dto.getB2bPrice());
        testPrice.setMrp(dto.getMrp());
        testPrice.setMmdPrice(dto.getMmdPrice());

        labRepository.save(lab);

        return detailedLabResourceAssembler.toModel(lab);
    }

    @Override
    public LabResource getLabById(String labId) {
        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new LabNotFoundException("Lab not found."));
        return detailedLabResourceAssembler.toModel(lab);
    }

    @Override
    public List<LabResource> getAllLabs() {
        List<Lab> labs = labRepository.findAll();
        return detailedLabResourceAssembler.toModelList(labs);
    }

    @Override
    public LabResource unmapTestToLab(String labId, String testId) {
        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new LabNotFoundException("Lab not found."));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("Test not found."));

        if (lab.getTestPrices() != null)
            lab.getTestPrices().removeIf(tp -> tp.getTestId().equals(testId));
        if (test.getTestMappedLabs() != null)
            test.getTestMappedLabs().removeIf(tml -> tml.getLabId().equals(labId));

        labRepository.save(lab);
        testRepository.save(test);

        return detailedLabResourceAssembler.toModel(lab);
    }

    @Override
    public LabResource registerLabWithLogo(MultipartFile file, RegisterLabDTO dto) {
        String s3Path = uploadLogo(file);
        Lab lab = modelMapper.map(dto, Lab.class);
        lab.setLogoS3Path(s3Path);
        labRepository.save(lab);
        return detailedLabResourceAssembler.toModel(lab);
    }

    @Override
    public InputStream downloadLabLogo(String logoS3Path) {
        try {
            String s3ObjectName = String.format("%s.%s", logoS3Path, THUMBNAIL_OUTPUT_FORMAT);
            S3Object s3Object = amazonS3Client.getObject(labLogo + "/logo", s3ObjectName);
            return s3Object.getObjectContent();
        } catch (SdkClientException e) {
            throw new RuntimeException("Error downloading logo: " + e.getMessage(), e);
        }
    }

    // ------------------- Helper Methods -------------------

    private Lab mapTestToLabEntity(Lab lab, LabTestMappingDTO dto) {
        if (lab.getTestPrices() == null) lab.setTestPrices(new ArrayList<>());

        Optional<TestPrice> existing = lab.getTestPrices().stream()
                .filter(tp -> tp.getTestId().equals(dto.getTestId()))
                .findFirst();

        if (existing.isPresent()) {
            TestPrice tp = existing.get();
            tp.setB2bPrice(dto.getB2bPrice());
            tp.setMrp(dto.getMrp());
            tp.setMmdPrice(dto.getMmdPrice());
            tp.setTestName(dto.getTestName());
        } else {
            TestPrice tp = new TestPrice();
            tp.setTestId(dto.getTestId());
            tp.setTestName(dto.getTestName());
            tp.setB2bPrice(dto.getB2bPrice());
            tp.setMrp(dto.getMrp());
            tp.setMmdPrice(dto.getMmdPrice());
            lab.getTestPrices().add(tp);
        }

        return labRepository.save(lab);
    }

    private void mapLabToTest(Test test, MapLabToTestDTO dto) {
        if (test.getTestMappedLabs() == null) test.setTestMappedLabs(new ArrayList<>());

        Optional<TestMappedLab> existing = test.getTestMappedLabs().stream()
                .filter(tml -> tml.getLabId().equals(dto.getLabId()))
                .findFirst();

        if (existing.isPresent()) {
            TestMappedLab tml = existing.get();
            tml.setLabName(dto.getLabName());
            tml.setB2bPrice(dto.getB2bPrice());
            tml.setMrp(dto.getMrp());
            tml.setMmdPrice(dto.getMmdPrice());
            tml.setLogoS3Path(dto.getLogoS3Path());
        } else {
            TestMappedLab tml = new TestMappedLab();
            tml.setLabId(dto.getLabId());
            tml.setLabName(dto.getLabName());
            tml.setB2bPrice(dto.getB2bPrice());
            tml.setMrp(dto.getMrp());
            tml.setMmdPrice(dto.getMmdPrice());
            tml.setLogoS3Path(dto.getLogoS3Path());
            test.getTestMappedLabs().add(tml);
        }

        testRepository.save(test);
    }

    private String uploadLogo(MultipartFile file) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("upload-", ".tmp");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            String s3ObjectName = System.currentTimeMillis() + "." + THUMBNAIL_OUTPUT_FORMAT;
            amazonS3Client.putObject(labLogo + "/logo", s3ObjectName, tempFile);

            return s3ObjectName;
        } catch (IOException | SdkClientException e) {
            throw new LabLogoPhotoProcessingException("Error uploading logo: " + e.getMessage(), e);
        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }
}

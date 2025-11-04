package com.TopLabBazaar2909.TLBnew2909.serviceImpl;


import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.TestService;
import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.entity.*;
import com.TopLabBazaar2909.TLBnew2909.exception.TestNotFoundException;
import com.TopLabBazaar2909.TLBnew2909.repository.CampTestMappingRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.LabRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.TestRepository;
import com.TopLabBazaar2909.TLBnew2909.resource.BasicInfoTestResource;
import com.TopLabBazaar2909.TLBnew2909.resource.FavouriteTestResource;
import com.TopLabBazaar2909.TLBnew2909.resource.LabSpecificTestPriceSummary;
import com.TopLabBazaar2909.TLBnew2909.resource.TestResource;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.BasicInfoTestResourceAssembler;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.DetailsTestResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private DetailsTestResourceAssembler detailsTestResourceAssembler;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private BasicInfoTestResourceAssembler basicInfoTestResourceAssembler;

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private CampTestMappingRepository campTestMappingRepository;

    @Override
    public List<TestResource> searchTest(TestSearchDTO testSearchDTO) {
        String raw = testSearchDTO.getPrefix();
        String escaped = escapeRegex(raw); // escape special characters

        return testRepository.findByNameContainingIgnoreCase(escaped)
                .stream()
                .map(detailsTestResourceAssembler::toModel)
                .collect(Collectors.toList());
    }


    private String escapeRegex(String input) {
        return input.replaceAll("([\\\\*+\\[\\](){}|.^$?])", "\\\\$1");
    }

    @Override
    public TestResource addTest(TestDTO testDTO) {
        Test test = new Test();

        test.setId(testDTO.getLabId());
        test.setName(testDTO.getName());
        test.setDescription(testDTO.getDescription());
        test.setSampleType(testDTO.getSampleType());
        test.setFavorite(testDTO.getFavorite());
        test.setSampleTubeType(testDTO.getSampleTubeType());
        test.setSpecimen(testDTO.getSpecimen());
        test.setNote(testDTO.getNote());

        Test savedTest = testRepository.save(test);

        return detailsTestResourceAssembler.toModel(savedTest);
    }



    @Override
    public BasicInfoTestResource updateFavouriteFlagInTest(UpdateFavouriteFlagDTO updateFavouriteFlagInTestDTO) {
        Test test = testRepository.findById(updateFavouriteFlagInTestDTO.getTestId())
                .orElseThrow(() -> new TestNotFoundException("Test not found."));

        test.setFavorite(updateFavouriteFlagInTestDTO.getFavourite());
        Test savedTest = testRepository.save(test);

        return basicInfoTestResourceAssembler.toModel(savedTest);
    }


    @Override
    public List<FavouriteTestResource> getAllFavouriteTestWRTLab(String labId) {
       Optional<Lab> optionalLab =  labRepository.findById(labId);
       List<Test> testList = testRepository.findByFavoriteTrue();
       return mapFavouriteTestsWithLabPrices(testList ,optionalLab.orElse(null)  );
    }
    @Override
    public List<FavouriteTestResource> getAllFavouriteTest() {
       List<Test> testList = testRepository.findByFavoriteTrue();
       return mapFavouriteTestsWithLabPrices(testList ,null  );
    }

    @Override
    public List<FavouriteTestResource> searchTestCombinedWithLabPrice(TestSearchDTO testSearchDTO) {
        Optional<Lab> optionalLab =  labRepository.findById(testSearchDTO.getLabId());
        List<Test> testList = testRepository.findByNameContainingIgnoreCase(testSearchDTO.getPrefix());
        return mapFavouriteTestsWithLabPrices(testList , optionalLab.orElse(null));
    }

    @Override
    public List<TestResource> getTestPriceWRTMappedLabs(CheckTestPriceDTO checkTestPriceDTO) {
        return detailsTestResourceAssembler
                .toCollectionModel(testRepository.findByIdIn(checkTestPriceDTO.getTestIds()))
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }



    private List<FavouriteTestResource> mapFavouriteTestsWithLabPrices(List<Test> favouriteTests, Lab lab) {
        List<FavouriteTestResource> result = new ArrayList<>();

        Map<String, TestPrice> testPriceMap = new HashMap<>();
        if (lab != null && lab.getTestPrices() != null) {
            for (TestPrice price : lab.getTestPrices()) {
                testPriceMap.put(price.getTestId(), price);
            }
        }

        for (Test test : favouriteTests) {
            FavouriteTestResource resource = new FavouriteTestResource();
            resource.setTestId(test.getId());
            resource.setTestName(test.getName());
            resource.setSampleType(test.getSampleType());
            resource.setSpecimen(test.getSpecimen());
            resource.setNote(test.getNote());
            resource.setSampleTubeType(test.getSampleTubeType());

            if (testPriceMap.containsKey(test.getId())) {
                TestPrice matchingPrice = testPriceMap.get(test.getId());
                resource.setLabId(lab.getId());
                resource.setLogoS3Path(lab.getLogoS3Path());
                resource.setB2bPrice(matchingPrice.getB2bPrice());
                resource.setMrp(matchingPrice.getMrp());
                resource.setMmdPrice(matchingPrice.getMmdPrice());
            }

            result.add(resource);
        }

        return result;
    }

    @Override
    public List<LabSpecificTestPriceSummary> getTestPriceWRTLab(CheckTestPriceDTO checkTestPriceDTO) {
        List<String> requiredTestIds = checkTestPriceDTO.getTestIds();

        // Step 1: Fetch labs that have any of the required testIds
        List<Lab> candidateLabs = labRepository.findLabsWithAnyTestIds(requiredTestIds);

        List<LabSpecificTestPriceSummary> result = new ArrayList<>();

        for (Lab lab : candidateLabs) {
            // Step 2: Filter only matched test prices
            List<TestPrice> matchedTestPrices = lab.getTestPrices().stream()
                    .filter(tp -> requiredTestIds.contains(tp.getTestId()))
                    .collect(Collectors.toList());

            if (!matchedTestPrices.isEmpty()) {
                LabSpecificTestPriceSummary summary = new LabSpecificTestPriceSummary();
                summary.setLabId(lab.getId());
                summary.setLabName(lab.getLabName());
                summary.setLogoS3Path(lab.getLogoS3Path());
                summary.setTestPrices(matchedTestPrices);

                result.add(summary);
            }
        }

        return result;
    }

    @Override
    public TestResource addExtraInfo(String testId, UpdateTestDTO updateTestDTO) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new TestNotFoundException("This test does not exist in the database")
        );

        test.setSpecimen(updateTestDTO.getSpecimen());
        test.setSampleTubeType(updateTestDTO.getSampleTubeType());
        test.setNote(updateTestDTO.getNote());


        test = testRepository.save(test);


        updateExtraInfoInsideTest(test);
        updateExtraInfoInCampTestMapping(test);


        return detailsTestResourceAssembler.toModel(test);
    }


    private void updateExtraInfoInCampTestMapping(Test test) {
        List<CampTestMapping> campTestMappings = campTestMappingRepository.findByTestId(test.getId());

        if (campTestMappings == null || campTestMappings.isEmpty()) {
            return;
        }

        for (CampTestMapping mapping : campTestMappings) {
            mapping.setSpecimen(test.getSpecimen());
            mapping.setNote(test.getNote());
            mapping.setSampleTubeType(test.getSampleTubeType());
        }

        campTestMappingRepository.saveAll(campTestMappings);
    }


    private void updateExtraInfoInsideTest(Test test) {
        if (test.getTestMappedLabs() == null) return;

        for (TestMappedLab testMappedLab : test.getTestMappedLabs()) {
            Optional<Lab> labOptional = labRepository.findById(testMappedLab.getLabId());
            if (labOptional.isPresent()) {
                Lab lab = labOptional.get();
                boolean updated = false;

                if (lab.getTestPrices() != null) {
                    for (TestPrice testPrice : lab.getTestPrices()) {
                        if (test.getId().equals(testPrice.getTestId())) {
                            testPrice.setSpecimen(test.getSpecimen());
                            testPrice.setNote(test.getNote());
                            testPrice.setSampleTubeType(test.getSampleTubeType());
                            updated = true;
                            break; // Exit after finding the matching TestPrice
                        }
                    }
                }

                if (updated) {
                    labRepository.save(lab);
                }
            }
        }
    }



}

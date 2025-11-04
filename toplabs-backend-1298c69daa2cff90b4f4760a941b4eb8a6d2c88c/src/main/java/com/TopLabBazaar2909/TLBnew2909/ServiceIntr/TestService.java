package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;


import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.resource.BasicInfoTestResource;
import com.TopLabBazaar2909.TLBnew2909.resource.FavouriteTestResource;
import com.TopLabBazaar2909.TLBnew2909.resource.LabSpecificTestPriceSummary;
import com.TopLabBazaar2909.TLBnew2909.resource.TestResource;

import java.util.List;

public interface TestService {

    //search
    List<TestResource> searchTest(TestSearchDTO testSearchDTO);

    //add
    TestResource addTest(TestDTO testDTO);

    BasicInfoTestResource updateFavouriteFlagInTest(UpdateFavouriteFlagDTO updateFavouriteFlagInTestDTO);

    List<FavouriteTestResource> getAllFavouriteTest();

    List<FavouriteTestResource> getAllFavouriteTestWRTLab(String labId);

    List<FavouriteTestResource> searchTestCombinedWithLabPrice(TestSearchDTO testSearchDTO);

    List<TestResource> getTestPriceWRTMappedLabs(CheckTestPriceDTO checkTestPriceDTO);

    List<LabSpecificTestPriceSummary> getTestPriceWRTLab(CheckTestPriceDTO checkTestPriceDTO);

    TestResource addExtraInfo(String testId, UpdateTestDTO updateTestDTO);
}

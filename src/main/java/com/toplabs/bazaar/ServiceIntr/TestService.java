package com.toplabs.bazaar.ServiceIntr;


import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.resource.BasicInfoTestResource;
import com.toplabs.bazaar.resource.FavouriteTestResource;
import com.toplabs.bazaar.resource.LabSpecificTestPriceSummary;
import com.toplabs.bazaar.resource.TestResource;

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

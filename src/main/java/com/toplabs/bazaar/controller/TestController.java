package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.TestService;
import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.resource.BasicInfoTestResource;
import com.toplabs.bazaar.resource.FavouriteTestResource;
import com.toplabs.bazaar.resource.LabSpecificTestPriceSummary;
import com.toplabs.bazaar.resource.TestResource;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Search for tests",
            description = "Accessible to all authenticated users"
    )
    public List<TestResource> searchTest(@RequestBody TestSearchDTO testSearchDTO){
        return testService.searchTest(testSearchDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Add a new test",
            description = "Accessible to all authenticated users"
    )
    public TestResource addTest(@RequestBody TestDTO testDTO){
        return testService.addTest(testDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/updateFavouriteFlagInTest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update favourite flag in test",
            description = "Accessible to all authenticated users"
    )
    public BasicInfoTestResource updateFavouriteFlagInTest(@RequestBody UpdateFavouriteFlagDTO updateFavouriteFlagInTestDTO){
        return testService.updateFavouriteFlagInTest(updateFavouriteFlagInTestDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/getFavouriteTestList/{labId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get all favourite tests for a lab",
            description = "Accessible to all authenticated users"
    )
    public List<FavouriteTestResource> getAllFavouriteTestForLab(@PathVariable String labId){
        return testService.getAllFavouriteTestWRTLab(labId);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/all/getFavouriteTestList",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get all favourite tests",
            description = "Accessible to all authenticated users"
    )
    public List<FavouriteTestResource> getAllFavouriteTest(){
        return testService.getAllFavouriteTest();
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/check/price",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get test price with mapped labs",
            description = "Accessible to all authenticated users"
    )
    public List<TestResource> getTestPriceWRTMappedLabs(@RequestBody CheckTestPriceDTO checkTestPriceDTO){
        return testService.getTestPriceWRTMappedLabs(checkTestPriceDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/search/test/combined/with/lab/price",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Search tests combined with lab prices",
            description = "Accessible to all authenticated users"
    )
    public List<FavouriteTestResource> searchTestForEntity(@RequestBody TestSearchDTO testSearchDTO){
        return testService.searchTestCombinedWithLabPrice(testSearchDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/search/test/price/WRT/labs",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Search test price wrt labs",
            description = "Accessible to all authenticated users"
    )
    public List<LabSpecificTestPriceSummary> getTestPriceWRTLab(@RequestBody CheckTestPriceDTO checkTestPriceDTO){
        return testService.getTestPriceWRTLab(checkTestPriceDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/addExtra/info/{testId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Add extra info to a test",
            description = "Accessible to all authenticated users"
    )
    public TestResource addExtraInfo(@PathVariable String testId,
                                     @RequestBody UpdateTestDTO updateTestDTO){
        return testService.addExtraInfo(testId, updateTestDTO);
    }
}

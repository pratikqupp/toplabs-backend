package com.toplabs.bazaar.repository;


import com.toplabs.bazaar.Enum.LocalCampStatus;
import com.toplabs.bazaar.dto.SearchCampDTO;
import com.toplabs.bazaar.entity.Camp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomCampRepository {

    Page<Camp> findCampsByStatus(LocalCampStatus status, Pageable pageable);

    List<Camp> searchCamps(SearchCampDTO searchCampDTO);


    Page<Camp> findCampsByUserIdAndStatus(String userId, LocalCampStatus status, Pageable pageable);

    List<Camp> findCampsByUserIdAndStatusAndSearch(String userId, LocalCampStatus status, String searchedPhrase);
}

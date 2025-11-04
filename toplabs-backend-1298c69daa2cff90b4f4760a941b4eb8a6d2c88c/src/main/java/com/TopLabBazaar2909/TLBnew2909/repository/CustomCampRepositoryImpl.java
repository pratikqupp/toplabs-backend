package com.TopLabBazaar2909.TLBnew2909.repository;

//import com.qup.microservices.provider.laboratory.dto.SearchCampDTO;
import com.TopLabBazaar2909.TLBnew2909.Enum.LocalCampStatus;
import com.TopLabBazaar2909.TLBnew2909.dto.SearchCampDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Camp;

//import com.qup.microservices.provider.laboratory.model.Camp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CustomCampRepositoryImpl implements CustomCampRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Camp> findCampsByStatus(LocalCampStatus status, Pageable pageable) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        String baseQuery = "SELECT c FROM Camp c WHERE ";
        String countQuery = "SELECT COUNT(c) FROM Camp c WHERE ";

        switch (status) {
            case PAST:
                baseQuery += "c.campEndDate < :today";
                countQuery += "c.campEndDate < :today";
                break;
            case ONGOING:
                baseQuery += "c.campStartDate <= :today AND c.campEndDate >= :today";
                countQuery += "c.campStartDate <= :today AND c.campEndDate >= :today";
                break;
            case UPCOMING:
                baseQuery += "c.campStartDate > :today";
                countQuery += "c.campStartDate > :today";
                break;
            default:
                throw new IllegalArgumentException("Invalid CampStatus: " + status);
        }

        TypedQuery<Camp> query = entityManager.createQuery(baseQuery + " ORDER BY c.campStartDate DESC", Camp.class);
        query.setParameter("today", today);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Camp> camps = query.getResultList();

        Long total = entityManager.createQuery(countQuery, Long.class)
                .setParameter("today", today)
                .getSingleResult();

        return new PageImpl<>(camps, pageable, total);
    }

    @Override
    public List<Camp> searchCamps(SearchCampDTO searchCampDTO) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        StringBuilder jpql = new StringBuilder("SELECT c FROM Camp c WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        // CampStatus filter
        if (searchCampDTO.getCampStatus() != null) {
            switch (searchCampDTO.getCampStatus()) {
                case PAST -> jpql.append("AND c.campEndDate < :today ");
                case ONGOING -> jpql.append("AND c.campStartDate <= :today AND c.campEndDate >= :today ");
                case UPCOMING -> jpql.append("AND c.campStartDate > :today ");
            }
        }

        // Search phrase filter
        if (searchCampDTO.getSearchedPhrase() != null && !searchCampDTO.getSearchedPhrase().isEmpty()) {
            jpql.append("AND LOWER(c.institutionName) LIKE LOWER(CONCAT('%', :phrase, '%')) ");
        }

        jpql.append("ORDER BY c.campStartDate DESC");

        TypedQuery<Camp> query = entityManager.createQuery(jpql.toString(), Camp.class);

        if (searchCampDTO.getCampStatus() != null) {
            query.setParameter("today", today);
        }
        if (searchCampDTO.getSearchedPhrase() != null && !searchCampDTO.getSearchedPhrase().isEmpty()) {
            query.setParameter("phrase", searchCampDTO.getSearchedPhrase());
        }

        return query.getResultList();
    }

    @Override
    public Page<Camp> findCampsByUserIdAndStatus(String userId, LocalCampStatus status, Pageable pageable) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        StringBuilder jpql = new StringBuilder("SELECT DISTINCT c FROM Camp c JOIN c.mappedUserIntoTheCampList u WHERE u.userId = :userId ");

        switch (status) {
            case PAST -> jpql.append("AND c.campEndDate < :today ");
            case ONGOING -> jpql.append("AND c.campStartDate <= :today AND c.campEndDate >= :today ");
            case UPCOMING -> jpql.append("AND c.campStartDate > :today ");
        }

        jpql.append("ORDER BY c.campStartDate DESC");

        TypedQuery<Camp> query = entityManager.createQuery(jpql.toString(), Camp.class);
        query.setParameter("userId", userId);
        query.setParameter("today", today);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Camp> camps = query.getResultList();

        // Count query
        String countJpql = jpql.toString().replaceFirst("SELECT DISTINCT c FROM", "SELECT COUNT(DISTINCT c) FROM");
        Long total = entityManager.createQuery(countJpql, Long.class)
                .setParameter("userId", userId)
                .setParameter("today", today)
                .getSingleResult();

        return new PageImpl<>(camps, pageable, total);
    }

    @Override
    public List<Camp> findCampsByUserIdAndStatusAndSearch(String userId, LocalCampStatus status, String searchedPhrase) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        StringBuilder jpql = new StringBuilder("SELECT DISTINCT c FROM Camp c JOIN c.mappedUserIntoTheCampList u WHERE u.userId = :userId ");

        switch (status) {
            case PAST -> jpql.append("AND c.campEndDate < :today ");
            case ONGOING -> jpql.append("AND c.campStartDate <= :today AND c.campEndDate >= :today ");
            case UPCOMING -> jpql.append("AND c.campStartDate > :today ");
        }

        if (searchedPhrase != null && !searchedPhrase.trim().isEmpty()) {
            jpql.append("AND LOWER(c.institutionName) LIKE LOWER(CONCAT('%', :phrase, '%')) ");
        }

        jpql.append("ORDER BY c.campStartDate DESC");

        TypedQuery<Camp> query = entityManager.createQuery(jpql.toString(), Camp.class);
        query.setParameter("userId", userId);
        query.setParameter("today", today);

        if (searchedPhrase != null && !searchedPhrase.trim().isEmpty()) {
            query.setParameter("phrase", searchedPhrase);
        }

        return query.getResultList();
    }
}

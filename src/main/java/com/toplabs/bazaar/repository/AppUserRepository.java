package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.AppUser;
import com.toplabs.bazaar.entity.Role22;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    /**
     * Find a user by their unique mobile number
     */
    Optional<AppUser> findByMobileNumber(String mobileNumber);

    /**
     * Find users by role with pagination
     */
    Page<AppUser> findByRole(Role22 role, Pageable pageable);

    /**
     * Find a specific user by role and mobile number
     */
    Optional<AppUser> findByRoleAndMobileNumber(Role22 role, String mobileNumber);

    /**
     * Find users whose IDs are in a given set
     */
    Page<AppUser> findByIdIn(Set<String> ids, Pageable pageable);

    /**
     * Optional method if you want to return list by mobile number
     */
    List<AppUser> findAllByMobileNumber(String mobileNumber);
}

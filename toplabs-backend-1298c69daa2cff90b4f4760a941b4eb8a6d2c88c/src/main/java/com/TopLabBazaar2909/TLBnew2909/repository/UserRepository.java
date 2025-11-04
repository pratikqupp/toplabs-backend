package com.TopLabBazaar2909.TLBnew2909.repository;

import com.TopLabBazaar2909.TLBnew2909.entity.UserTeam;
import com.TopLabBazaar2909.TLBnew2909.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {


    List<AppUser> findByDepartment_Id(String id);

    List<AppUser> findByRoleId(String id);

    List<AppUser> findByPositionId(Long id);



    Optional<UserTeam> findByEmail(String email);


    Optional<AppUser> findByMobileNumber(String mobileNumber);


}

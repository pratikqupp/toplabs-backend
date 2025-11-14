package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserPatientRepository extends JpaRepository<UserPatient, Long> {
    Optional<UserPatient> findByPhone(String phone);
    void deleteByPhone(String phone);

}

package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.Leads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LeadsRepository extends JpaRepository<Leads, String> {
    List<Leads> findByUser_MobileNumber(String mobileNumber);
}

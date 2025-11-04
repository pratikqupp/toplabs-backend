package com.TopLabBazaar2909.TLBnew2909.repository;

import com.TopLabBazaar2909.TLBnew2909.entity.Leads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LeadsRepository extends JpaRepository<Leads, String> {
    List<Leads> findByUser_MobileNumber(String mobileNumber);
}

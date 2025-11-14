package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.Otp14;
import com.toplabs.bazaar.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<Otp14, Long> {
    List<Otp14> findByUserAndUsedFalse(AppUser user);
    Optional<Otp14> findFirstByUserAndUsedFalseOrderByExpiryDesc(AppUser user);
}

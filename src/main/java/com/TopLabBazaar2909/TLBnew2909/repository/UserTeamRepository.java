package com.TopLabBazaar2909.TLBnew2909.repository;

import com.TopLabBazaar2909.TLBnew2909.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    Optional<UserTeam> findByEmail(String email);
    Optional<UserTeam> findByMobile(String mobile);
}

package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.UserTeamDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.UserTeam;

import java.util.List;
import java.util.Optional;

public interface UserTeamService {
    UserTeam createUser(UserTeamDTO dto);
    UserTeam updateUser(Long id, UserTeam userTeam);
    void deleteUser(Long id);
    Optional<UserTeam> getUserById(Long id);
    Optional<UserTeam> getUserByEmail(String email);
    Optional<UserTeam> getUserByMobile(String mobile);
    List<UserTeam> getAllUsers();
}

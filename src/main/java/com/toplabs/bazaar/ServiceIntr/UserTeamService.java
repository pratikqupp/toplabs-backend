package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.UserTeam;

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

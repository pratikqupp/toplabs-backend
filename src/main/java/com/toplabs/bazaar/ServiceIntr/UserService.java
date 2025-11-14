package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.UserTeam;
import com.toplabs.bazaar.entity.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> getAllUsers();

    AppUser createUser(UserTeamDTO user);

    AppUser updateUser(String userId, UserTeam userDetails);

    AppUser getUserById(String userId);

    AppUser getUserByMobile(String mobile);

    boolean validateUserByMobile(String mobile);

    UserTeam validateUser(String email, String password, String roleId, String departmentId, String positionId);

    AppUser deactivateUser(String userId);
}

package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.UserTeamDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.UserTeam;
import com.TopLabBazaar2909.TLBnew2909.entity.AppUser;

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

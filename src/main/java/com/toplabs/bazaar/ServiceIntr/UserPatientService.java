package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.UserPatientDTO;
import com.toplabs.bazaar.entity.UserPatient;

import java.util.List;
import java.util.Optional;

public interface UserPatientService {
    List<UserPatient> getAllUsers();

    Optional<UserPatient> getUserByPhone(String phone);

    UserPatientDTO createOrUpdateUser(UserPatientDTO userDTO);


    Optional<UserPatient> updateUser(String phone, UserPatient updateUser);

    boolean deleteUserByPhone(String phone);
}

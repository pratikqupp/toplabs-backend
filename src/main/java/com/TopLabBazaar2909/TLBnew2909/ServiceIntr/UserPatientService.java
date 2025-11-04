package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.UserPatientDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.UserPatient;

import java.util.List;
import java.util.Optional;

public interface UserPatientService {
    List<UserPatient> getAllUsers();

    Optional<UserPatient> getUserByPhone(String phone);

    UserPatientDTO createOrUpdateUser(UserPatientDTO userDTO);


    Optional<UserPatient> updateUser(String phone, UserPatient updateUser);

    boolean deleteUserByPhone(String phone);
}

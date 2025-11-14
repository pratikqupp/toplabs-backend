package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.ServiceIntr.UserPatientService;
import com.toplabs.bazaar.dto.UserPatientDTO;
import com.toplabs.bazaar.entity.UserPatient;
import com.toplabs.bazaar.repository.UserPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserPatientServiceImpl implements UserPatientService {
    @Autowired
    private UserPatientRepository userPatientRepository;

    @Override
    public List<UserPatient> getAllUsers() {
        return userPatientRepository.findAll();
    }

    @Override
    public Optional<UserPatient> getUserByPhone(String phone) {
        return userPatientRepository.findByPhone(phone);
    }

    @Override
    public UserPatientDTO createOrUpdateUser(UserPatientDTO userDTO) {
        // Check if user already exists by phone
        UserPatient existingUser = userPatientRepository.findByPhone(userDTO.getPhone()).orElse(null);

        UserPatient user;
        if (existingUser != null) {
            // Update existing user details
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setAddress(userDTO.getAddress());
            existingUser.setPostalCode(userDTO.getPostalCode());
            existingUser.setGender(userDTO.getGender());
            existingUser.setDateOfBirth(userDTO.getDateOfBirth());
            user = userPatientRepository.save(existingUser);
        } else {
            // Create new user
            user = new UserPatient();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
            user.setPostalCode(userDTO.getPostalCode());
            user.setGender(userDTO.getGender());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setCreatedAt(LocalDate.now());
            user = userPatientRepository.save(user);
        }

        // Convert back to DTO for response
        return new UserPatientDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getPostalCode(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getCreatedAt()
        );
    }


    @Override
    public Optional<UserPatient> updateUser(String phone, UserPatient updateUser) {
        return userPatientRepository.findByPhone(phone).map(existingUser -> {
            existingUser.setName(updateUser.getName());
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setAddress(updateUser.getAddress());
            existingUser.setPostalCode(updateUser.getPostalCode());
            existingUser.setGender(updateUser.getGender());
            existingUser.setDateOfBirth(updateUser.getDateOfBirth());
            return userPatientRepository.save(existingUser);
        });
    }

    @Override
    public boolean deleteUserByPhone(String phone) {
        return userPatientRepository.findByPhone(phone).map(user -> {
            userPatientRepository.deleteByPhone(phone);
            return true;
        }).orElse(false);
    }
}

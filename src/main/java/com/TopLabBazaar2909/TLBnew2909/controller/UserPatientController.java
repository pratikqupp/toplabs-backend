package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.UserPatientService;
import com.TopLabBazaar2909.TLBnew2909.dto.UserPatientDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.UserPatient;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class UserPatientController {
    private final UserPatientService userPatientService;

    public UserPatientController(UserPatientService userPatientService) {
        this.userPatientService = userPatientService;
    }

    //  Get all patients
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/getalluser")
    public ResponseEntity<List<UserPatient>> getAllUsers() {
        return ResponseEntity.ok(userPatientService.getAllUsers());
    }

    //  Get patient by phone
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/:mobilenumber")
    public ResponseEntity<UserPatient> getUserByPhone(@PathVariable String phone) {
        Optional<UserPatient> user = userPatientService.getUserByPhone(phone);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  Create new patient (or update if phone exists)
   //@PreAuthorize("isAuthenticated()")
    @PostMapping("/createuser")
    public ResponseEntity<UserPatientDTO> createOrUpdateUser(@RequestBody UserPatientDTO userDTO) {
        UserPatientDTO savedUser = userPatientService.createOrUpdateUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    // Update patient by phone
   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/:mobilenumber")
    public ResponseEntity<UserPatient> updateUser(@PathVariable String phone,
                                                  @RequestBody UserPatient updateUser) {
        Optional<UserPatient> updated = userPatientService.updateUser(phone, updateUser);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  Delete patient by phone
   //@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/:mobilenumber")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable String phone) {
        boolean deleted = userPatientService.deleteUserByPhone(phone);
        if (deleted) {
            return ResponseEntity.ok("User with phone " + phone + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

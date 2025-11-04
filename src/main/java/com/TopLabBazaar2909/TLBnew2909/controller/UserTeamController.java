package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.UserTeamService;
import com.TopLabBazaar2909.TLBnew2909.dto.UserTeamDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.UserTeam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-teams")
public class UserTeamController {
    private final UserTeamService userTeamService;

    public UserTeamController(UserTeamService userTeamService) {
        this.userTeamService = userTeamService;
    }


    //  Create or Update UserTeam
   //@PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<UserTeam> createUser(@RequestBody UserTeamDTO dto) {
        UserTeam savedUser = userTeamService.createUser(dto);
        return ResponseEntity.ok(savedUser);
    }

    //  Update User
   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<UserTeam> updateUser(@PathVariable Long id, @RequestBody UserTeam userTeam) {
        return ResponseEntity.ok(userTeamService.updateUser(id, userTeam));
    }

    //  Delete User
   //@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userTeamService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully with id: " + id);
    }

    //  Get User by ID
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UserTeam> getUserById(@PathVariable Long id) {
        return userTeamService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Get User by Email
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserTeam> getUserByEmail(@PathVariable String email) {
        return userTeamService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Get User by Mobile
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<UserTeam> getUserByMobile(@PathVariable String mobile) {
        return userTeamService.getUserByMobile(mobile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Get All Users
   //@PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<UserTeam>> getAllUsers() {
        return ResponseEntity.ok(userTeamService.getAllUsers());
    }

    //  Get All Teams (different endpoint to avoid ambiguity)
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/teams")
    public ResponseEntity<List<UserTeam>> getTeams() {
        List<UserTeam> teams = userTeamService.getAllUsers();
        return ResponseEntity.ok(teams);
    }
}

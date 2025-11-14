package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.UserService;
import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.UserTeam;
import com.toplabs.bazaar.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth-team-services/team")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getalluser")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/createUser")
    public ResponseEntity<AppUser> createUser(@RequestBody UserTeamDTO userDTO) {
        AppUser createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }


   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable String id, @RequestBody UserTeam userDetails) {
        AppUser updated = userService.updateUser(id, userDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable String id) {
        AppUser user = userService.getUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }




   //@PreAuthorize("isAuthenticated()")
    @PostMapping("/validateUser")
    public ResponseEntity<String> validateUser(@RequestParam String email,
                                               @RequestParam String password,
                                               @RequestParam String roleId,
                                               @RequestParam String departmentId,
                                               @RequestParam String positionId) {
        UserTeam user = userService.validateUser(email, password, roleId, departmentId, positionId);
        if (user == null) return ResponseEntity.status(403).body("Invalid credentials or inactive user");
        return ResponseEntity.ok("Login successful");
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<AppUser> deactivateUser(@PathVariable String id) {
        AppUser user = userService.deactivateUser(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
}

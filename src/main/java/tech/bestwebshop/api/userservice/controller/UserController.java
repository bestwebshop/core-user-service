package tech.bestwebshop.api.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.bestwebshop.api.userservice.exception.ResourceNotFoundException;
import tech.bestwebshop.api.userservice.model.dataobject.RoleDO;
import tech.bestwebshop.api.userservice.model.dataobject.UserDO;
import tech.bestwebshop.api.userservice.model.ressource.User;
import tech.bestwebshop.api.userservice.repository.RoleRepository;
import tech.bestwebshop.api.userservice.repository.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        UserDO userFromDB = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

        RoleDO roleFromDB = roleRepository.findById(userFromDB.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", userFromDB.getRoleId())
        );

        return new User(userFromDB.getId(), userFromDB.getUsername(), userFromDB.getFirstname(), userFromDB.getLastname(),
                userFromDB.getPassword(), roleFromDB);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        UserDO userToSave = new UserDO(user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(),
                user.getRole().getId());
        RoleDO roleToSave = new RoleDO(user.getRole().getId(), user.getRole().getTyp(), user.getRole().getLevel());
        UserDO savedUser = userRepository.save(userToSave);
        RoleDO savedRole = roleRepository.findById(savedUser.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", savedUser.getRoleId()));
        return new User(savedUser.getId(), savedUser.getUsername(), savedUser.getFirstname(), savedUser.getLastname(),
                savedUser.getPassword(), savedRole);
    }
}
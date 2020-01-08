package tech.bestwebshop.api.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bestwebshop.api.userservice.exception.ResourceNotFoundException;
import tech.bestwebshop.api.userservice.model.dataobject.RoleDO;
import tech.bestwebshop.api.userservice.model.dataobject.SignInDTO;
import tech.bestwebshop.api.userservice.model.dataobject.UserDO;
import tech.bestwebshop.api.userservice.model.ressource.User;
import tech.bestwebshop.api.userservice.repository.RoleRepository;
import tech.bestwebshop.api.userservice.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users/{id}")
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

    @PostMapping("/users")
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

    @PostMapping("/session")
    public ResponseEntity<User> signIn(@RequestBody @Valid SignInDTO signInDTO){
        Optional<UserDO> optionalUserDO = userRepository.findByUsername(signInDTO.getUsername());
        if(!optionalUserDO.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserDO dbUser = optionalUserDO.get();
        if(!(signInDTO.getPassword().equals(dbUser.getPassword()))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Optional<RoleDO> optionalRoleDO = roleRepository.findById(dbUser.getRoleId());
            if(!optionalRoleDO.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            RoleDO roleDO = optionalRoleDO.get();
            User user = new User(dbUser.getId(), dbUser.getUsername(), dbUser.getFirstname(), dbUser.getLastname(),
                    dbUser.getPassword(), roleDO);
            return ResponseEntity.ok(user);
        }
    }
}
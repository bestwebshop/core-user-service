package tech.bestwebshop.api.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import tech.bestwebshop.api.userservice.exception.ResourceNotFoundException;
import tech.bestwebshop.api.userservice.model.dataobject.RoleDO;
import tech.bestwebshop.api.userservice.model.dataobject.SignInDTO;
import tech.bestwebshop.api.userservice.model.dataobject.UserDO;
import tech.bestwebshop.api.userservice.model.dataobject.UserToSaveDO;
import tech.bestwebshop.api.userservice.model.ressource.User;
import tech.bestwebshop.api.userservice.repository.RoleRepository;
import tech.bestwebshop.api.userservice.repository.UserRepository;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed({"USER"})
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Integer userId, OAuth2Authentication auth) {
        Optional<UserDO> optionalUserDO = userRepository.findById(userId);
        if(!optionalUserDO.isPresent()){
            return ResponseEntity.notFound().build();
        }
        UserDO userDO = optionalUserDO.get();
        System.out.println("AUTH-USER: " + auth.getName());
        if(!auth.getName().equals("admin")
                && !userDO.getUsername().equals(auth.getName())){ // you will only be able to access your own data if you're not admin
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<RoleDO> optionalRoleDO = roleRepository.findById(userDO.getRoleId());
        if(!optionalRoleDO.isPresent()){
            return ResponseEntity.notFound().build();
        }
        RoleDO roleFromDB = optionalRoleDO.get();

        User user = this.buildUser(userDO, roleFromDB);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUserByUsername(@RequestParam(defaultValue = "") String username){
        if(username.equals("")){
            return ResponseEntity.notFound().build();
        }
        Optional<UserDO> optionalUserDO = userRepository.findByUsername(username);
        if(!optionalUserDO.isPresent()){
            return ResponseEntity.notFound().build();
        }
        UserDO userDO = optionalUserDO.get();

        Optional<RoleDO> optionalRoleDO = roleRepository.findById(userDO.getRoleId());
        if(!optionalRoleDO.isPresent()){
            return ResponseEntity.notFound().build();
        }
        RoleDO roleFromDB = optionalRoleDO.get();

        User user = this.buildUser(userDO, roleFromDB);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserToSaveDO userToSaveDO) {
        Optional<RoleDO> optionalRoleDO = roleRepository.findByLevel(userToSaveDO.getRoleLevel());
        if(!optionalRoleDO.isPresent()){
            return ResponseEntity.notFound().build();
        }
        RoleDO roleDO = optionalRoleDO.get();
        UserDO userToSave = new UserDO(userToSaveDO.getUsername(), userToSaveDO.getFirstname(), userToSaveDO.getLastname(), userToSaveDO.getPassword(),
                roleDO.getId());
        try {
            UserDO savedUser = userRepository.save(userToSave);

            User user = this.buildUser(savedUser, roleDO);
                    new User(savedUser.getId(), savedUser.getUsername(), savedUser.getFirstname(),
                    savedUser.getLastname(), savedUser.getPassword(), roleDO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/session")
    public ResponseEntity<User> signIn(@RequestBody @Valid SignInDTO signInDTO) {
        Optional<UserDO> optionalUserDO = userRepository.findByUsername(signInDTO.getUsername());
        if (!optionalUserDO.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserDO dbUser = optionalUserDO.get();
        if (!(signInDTO.getPassword().equals(dbUser.getPassword()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Optional<RoleDO> optionalRoleDO = roleRepository.findById(dbUser.getRoleId());
            if (!optionalRoleDO.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            RoleDO roleDO = optionalRoleDO.get();
            //User user = new User(dbUser.getId(), dbUser.getUsername(), dbUser.getFirstname(), dbUser.getLastname(),
             //       dbUser.getPassword(), roleDO);
            User user = this.buildUser(dbUser, roleDO);
            return ResponseEntity.ok(user);
        }
    }

    private User buildUser(UserDO userDO, RoleDO roleDO){
        return new User(userDO.getId(), userDO.getUsername(), userDO.getFirstname(), userDO.getLastname(),
                userDO.getPassword(), roleDO);
    }
}
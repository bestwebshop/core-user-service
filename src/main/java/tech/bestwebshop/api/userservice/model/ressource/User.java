package tech.bestwebshop.api.userservice.model.ressource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.bestwebshop.api.userservice.model.dataobject.RoleDO;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @NonNull
    private int id;

    @NonNull
    private String username;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @NonNull
    private String password;

    @NonNull
    private RoleDO role;
}


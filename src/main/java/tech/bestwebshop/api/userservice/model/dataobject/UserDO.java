package tech.bestwebshop.api.userservice.model.dataobject;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "customer")
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    @NonNull
    private String username;

    @Column(name = "name", nullable = false)
    @NonNull
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NonNull
    private String lastname;

    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    @Column(name="role", nullable = false)
    @NonNull
    private int roleId;

}

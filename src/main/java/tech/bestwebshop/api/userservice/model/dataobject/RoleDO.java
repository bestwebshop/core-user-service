package tech.bestwebshop.api.userservice.model.dataobject;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "role")
@NoArgsConstructor
@RequiredArgsConstructor
public class RoleDO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NonNull
    private int id;

    @Column(name = "type")
    @NonNull
    private String typ;

    @Column(name = "level1")
    @NonNull
    private int level;
}


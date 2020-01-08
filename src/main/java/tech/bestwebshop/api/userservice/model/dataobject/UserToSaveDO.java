package tech.bestwebshop.api.userservice.model.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserToSaveDO {

    @NonNull
    @NotEmpty
    private String username;

    @NonNull
    @NotEmpty
    private String firstname;

    @NonNull
    @NotEmpty
    private String lastname;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    @PositiveOrZero
    private int roleLevel;

}

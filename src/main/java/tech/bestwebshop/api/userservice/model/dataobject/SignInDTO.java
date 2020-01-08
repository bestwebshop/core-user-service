package tech.bestwebshop.api.userservice.model.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SignInDTO {

    @NonNull
    @NotEmpty
    private String username;

    @NonNull
    @NotEmpty
    private String password;
}

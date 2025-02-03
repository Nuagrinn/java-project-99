package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @NotBlank
    @Email
    private String email;

    private String firstName;

    private String lastName;

    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    private String encryptedPassword;
}

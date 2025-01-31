package hexlet.code.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusCreateDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String slug;
}

package hexlet.code.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private LocalDate createdAt;
}

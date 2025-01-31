package hexlet.code.dto;

import org.openapitools.jackson.nullable.JsonNullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelUpdateDTO {
    private JsonNullable<String> name;
}

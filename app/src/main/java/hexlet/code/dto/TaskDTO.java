package hexlet.code.dto;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDTO {

    private Long id;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @JsonProperty("title")
    private String name;

    @JsonProperty("content")
    private String description;

    @JsonProperty("status")
    private String taskStatusSlug;

    private Set<Long> taskLabelIds;

    private Integer index;

    private LocalDate createdAt;
}

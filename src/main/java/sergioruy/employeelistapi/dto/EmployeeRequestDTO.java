package sergioruy.employeelistapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    private String code;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String firstName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String lastName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String emailId;

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}

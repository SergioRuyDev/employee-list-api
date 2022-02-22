package sergioruy.employeelistapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private String code;
    private String firstName;
    private String lastName;
    private String emailId;

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }

}

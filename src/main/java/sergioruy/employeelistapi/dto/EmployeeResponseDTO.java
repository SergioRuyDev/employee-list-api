package sergioruy.employeelistapi.dto;

import lombok.*;

import javax.persistence.PrePersist;
import java.util.UUID;

@Getter
@Setter
@ToString
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

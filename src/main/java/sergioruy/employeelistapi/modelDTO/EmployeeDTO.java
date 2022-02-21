package sergioruy.employeelistapi.modelDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO {

    private String firstName;
    private String lastName;
    private String emailId;

    //todo check if need uuid
}

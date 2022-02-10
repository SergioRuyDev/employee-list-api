package sergioruy.employeelistapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import sergioruy.employeelistapi.model.Employee;
import sergioruy.employeelistapi.repository.EmployeeRepository;
import sergioruy.employeelistapi.service.EmployeeService;
import sergioruy.employeelistapi.service.impl.EmployeeServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;



    @BeforeEach
    void setUp() {

        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);

    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmailId(employee.getEmailId())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository); // make sure is mocked
        System.out.println(employeeService); // make sure about is mocked

        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee); // just for make sure that are not null
        //then
        Assertions.assertThat(savedEmployee).isNotNull();

    }
}
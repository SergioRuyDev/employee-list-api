package sergioruy.employeelistapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sergioruy.employeelistapi.exception.ResourceNotFoundException;
import sergioruy.employeelistapi.model.Employee;
import sergioruy.employeelistapi.repository.EmployeeRepository;
import sergioruy.employeelistapi.service.impl.EmployeeServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();

    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given
        given(employeeRepository.findByEmailId(employee.getEmailId())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository); // make sure is mocked
        System.out.println(employeeService); // make sure about is mocked

        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee); // just for make sure that are not null
        //then
        assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for saveEmployee method with throws exception
    @DisplayName("JUnit test for saveEmployee method with throws exception")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {
        //given
        given(employeeRepository.findByEmailId(employee.getEmailId())).willReturn(Optional.of(employee));

        System.out.println(employeeRepository); // make sure is mocked
        System.out.println(employeeService); // make sure about is mocked

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then
        verify(employeeRepository, never()).save(any(Employee.class));

    }
}
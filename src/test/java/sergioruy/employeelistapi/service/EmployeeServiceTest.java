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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {

        //given

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .emailId("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for getAllEmployees method (negative scenario)
    @DisplayName("JUnit test for getAllEmployees method (negative scenario")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

        //given

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .emailId("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //JUnit test for getEmployeeById method operation
    @DisplayName("JUnit test for getEmployeeById method operation")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

        //given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then
        assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmailId("junior@gmail.com");
        employee.setFirstName("Steve");

        //when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then
        assertThat(updatedEmployee.getEmailId()).isEqualTo("junior@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Steve");

    }

    //JUnit test for delete employee by Id
    @DisplayName("JUnit test for delete employee by Id")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {

        //given
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(1L);

        //when
        employeeService.deleteEmployee(1L);

        //then
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }
}
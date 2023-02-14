package sergioruy.employeelistapi.integration.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sergioruy.employeelistapi.model.Employee;
import sergioruy.employeelistapi.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryITcontainer extends AbstractionContainerBaseTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
    }

    //JUnit test for save Employee operation
    @DisplayName("JUnit test for save Employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //when
        Employee employeeSaved = employeeRepository.save(employee);

        //then
        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getId()).isGreaterThan(0);
    }

    //JUnit test for findAll Employees operation
    @DisplayName("JUnit test for findAll Employees operation")
    @Test
    public void givenListObjects_whenFindAll_thenReturnListOfEmployees() {

        //given
        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .emailId("john@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        //when
        List<Employee> employeeList = employeeRepository.findAll();

        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for find Employee by Id operation
    @DisplayName("JUnit test for find Employee by Id operation")
    @Test
    public void givenEmployeeObject_whenFindEmployeeById_thenReturnEmployee() {

        //given
        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeDB).isNotNull();

    }


    //JUnit test for find Employee by email operation
    @DisplayName("JUnit test for find Employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given
        employeeRepository.save(employee);
        //when
        Employee employeeDB = employeeRepository.findByEmailId(employee.getEmailId()).get();

        //then
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getEmailId()).isEqualTo("sergio@gmail.com");

    }

    //JUnit test for Update Employee operation
    @DisplayName("JUnit test for Update Employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given
        employeeRepository.save(employee);

        //when
        Employee employeeSaved = employeeRepository.findById(employee.getId()).get();
        employeeSaved.setEmailId("junior@gmail.com");
        employeeSaved.setLastName("Ruy");
        Employee updatedEmployee = employeeRepository.save(employeeSaved);

        //then
        assertThat(updatedEmployee.getEmailId()).isEqualTo("junior@gmail.com");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Ruy");

    }

    //JUnit test for delete Employee operation
    @DisplayName("JUnit test for delete Employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        //given
        employeeRepository.save(employee);

        //when
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    // JPQL cannot instance outside of the scope
        //given
        Employee employee = Employee.builder()
                .firstName("Sergio")
                .lastName("Junior")
                .emailId("sergio@gmail.com")
                .build();

        employeeRepository.save(employee);
        String firstName = "Sergio";
        String lastName = "Junior";

        //when
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using JPQL with named params
    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        //given
        Employee employee = Employee.builder()
                .firstName("Sergio")
                .lastName("Junior")
                .emailId("sergio@gmail.com")
                .build();

        employeeRepository.save(employee);
        String firstName = "Sergio";
        String lastName = "Junior";

        //when
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using native SQL with index params
    @DisplayName("JUnit test for custom query using native SQL with index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

        //given
        employeeRepository.save(employee);

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParam_thenReturnEmployeeObject() {

        //given
        employeeRepository.save(employee);

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        //then
        assertThat(savedEmployee).isNotNull();

    }

}

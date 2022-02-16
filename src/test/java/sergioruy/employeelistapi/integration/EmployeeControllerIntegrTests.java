package sergioruy.employeelistapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sergioruy.employeelistapi.model.Employee;
import sergioruy.employeelistapi.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }


    //JUnit test for getAll Employees
    @DisplayName("JUnit test for getAll Employees")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {

        //given - is a precondition or a setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Sergio").lastName("Ruy").emailId("sergio@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").emailId("tony@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        //when - is the action or the behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        //then - verify the result
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));

    }

    //Junit test for Get Employee By Id in Positive scenario
    @DisplayName("Junit test for Get Employee By Id in Positive scenario")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given - is a precondition or a setup
        Employee employee = Employee.builder()
                .firstName("Sergio Ruy")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - is the action or the behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employee.getId()));

        //then - verify the result
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.emailId", is(employee.getEmailId())));

    }

    //Junit test for Get Employee By Id in Negative scenario
    @DisplayName("Junit test for Get Employee By Id in Negative scenario")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

        //given - is a precondition or a setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Sergio Ruy")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - is the action or the behavior we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));

        //then - verify the result
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //JUnit test of method post operation
    @DisplayName("JUnit test of method post operation")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given
        Employee employee = Employee.builder()
                .firstName("Sergio Ruy")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.emailId", is(employee.getEmailId())));
    }

    //JUnit test of method update operation - positive scenario
    @DisplayName("JUnit test of update operation - positive scenario")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {

        // given
        Employee savedEmployee = Employee.builder()
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ruy")
                .lastName("Junior")
                .emailId("junior@gmail.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.emailId", is(updatedEmployee.getEmailId())));
    }

    //JUnit test of method update operation - negative scenario
    @DisplayName("JUnit test of update operation - negative scenario")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnStatusNotFound() throws Exception {

        // given
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ruy")
                .lastName("Junior")
                .emailId("junior@gmail.com")
                .build();


        // when
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(print()).
                andExpect(status().isNotFound());
    }

    //Junit test for delete employee in Positive scenario
    @DisplayName("Junit test for delete employee in Positive scenario")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnStatusOk() throws Exception {

        //given - is a precondition or a setup
        Employee savedEmployee = Employee.builder()
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        //when - is the action or the behavior we are going to test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", savedEmployee.getId()));

        //then - verify the result
        response.andDo(print())
                .andExpect(status().isOk());
    }

    //Junit test for delete employee in Negative scenario
    @DisplayName("Junit test for delete employee in Negative scenario")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNotFound() throws Exception {

        //given - is a precondition or a setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Sergio")
                .lastName("Ruy")
                .emailId("sergio@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        //when - is the action or the behavior we are going to test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));

        //then - verify the result
        response.andDo(print())
                .andExpect(status().isNotFound());
    }
}

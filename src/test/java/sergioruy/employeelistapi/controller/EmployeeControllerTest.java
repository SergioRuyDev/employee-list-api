package sergioruy.employeelistapi.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import sergioruy.employeelistapi.model.Employee;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.employeeController);
    }

    @Test
    public void shouldReturnSuccess_WhenGetEmployee() {

        when(this.employeeController.getEmployeeById(1L))
                .thenReturn(new Employee("Sergio", "Ruy", "sergioruy@gmail.com"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/employees/{id}", 1L)
                .then()
                .statusCode(OK);
    }
}

package sergioruy.employeelistapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sergioruy.employeelistapi.exception.ResourceNotFoundException;
import sergioruy.employeelistapi.model.Employee;
import sergioruy.employeelistapi.repository.EmployeeRepository;
import sergioruy.employeelistapi.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Employees")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @ApiOperation("List of employees")
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @ApiOperation("Register a new employee")
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

//    @ApiOperation("Search a employee by ID")
//    @GetMapping("/employees/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Employee getEmployeeById(@PathVariable Long id) {
//        return Employee employee = employeeService.getEmployeeById(id);
//    }

    // get employee by id
    @ApiOperation("Search a employee by ID")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id : " + id));
        return ResponseEntity.ok(employee);
    }

    // update the employee
    @ApiOperation("Update employee details by ID")
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id : " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updateEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    // Delete the employee
    @ApiOperation("Remove a employee by ID")
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id : " + id));
        employeeService.deleteEmployee(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

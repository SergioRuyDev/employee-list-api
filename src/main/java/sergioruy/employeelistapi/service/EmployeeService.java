package sergioruy.employeelistapi.service;

import sergioruy.employeelistapi.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

}

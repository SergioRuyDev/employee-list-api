package sergioruy.employeelistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sergioruy.employeelistapi.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

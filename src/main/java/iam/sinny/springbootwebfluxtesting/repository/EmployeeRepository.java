package iam.sinny.springbootwebfluxtesting.repository;

import iam.sinny.springbootwebfluxtesting.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}
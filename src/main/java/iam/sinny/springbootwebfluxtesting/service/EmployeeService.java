package iam.sinny.springbootwebfluxtesting.service;

import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO);

    Mono<EmployeeDTO> getEmployeeById(String id);

    Flux<EmployeeDTO> getAllEmployees();

    Mono<Void> deleteEmployeeById(String id);

    Mono<EmployeeDTO> updateEmployee(String id, EmployeeDTO employeeDTO);
}
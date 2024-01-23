package iam.sinny.springbootwebfluxtesting.service.impl;

import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import iam.sinny.springbootwebfluxtesting.entity.Employee;
import iam.sinny.springbootwebfluxtesting.mapper.EmployeeMapper;
import iam.sinny.springbootwebfluxtesting.repository.EmployeeRepository;
import iam.sinny.springbootwebfluxtesting.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);

        return employeeRepository.save(employee).map(EmployeeMapper::toDTO);
    }

    @Override
    public Mono<EmployeeDTO> getEmployeeById(String id) {
        return employeeRepository.findById(id).map(EmployeeMapper::toDTO);
    }

    @Override
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().map(EmployeeMapper::toDTO).switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<Void> deleteEmployeeById(String id) {
        return employeeRepository.deleteById(id);
    }

    @Override
    public Mono<EmployeeDTO> updateEmployee(String id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    employee.setFirstName(employeeDTO.getFirstName());
                    employee.setLastName(employeeDTO.getLastName());
                    employee.setEmail(employeeDTO.getEmail());
                    return employeeRepository.save(employee);
                })
                .map(EmployeeMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAllEmployees() {
        return employeeRepository.deleteAll();
    }
}
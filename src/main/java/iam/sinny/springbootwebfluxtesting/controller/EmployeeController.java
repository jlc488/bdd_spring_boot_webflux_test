package iam.sinny.springbootwebfluxtesting.controller;

import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import iam.sinny.springbootwebfluxtesting.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    // reactive save employee REST API
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EmployeeDTO> getEmployeeById(@PathVariable("id") String id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EmployeeDTO> updateEmployee(@PathVariable("id") String id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployeeById(@PathVariable("id") String id) {
        return employeeService.deleteEmployeeById(id);
    }



}
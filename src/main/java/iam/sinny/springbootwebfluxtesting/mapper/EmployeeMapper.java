package iam.sinny.springbootwebfluxtesting.mapper;

import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import iam.sinny.springbootwebfluxtesting.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
    }

    public static Employee toEntity(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getId(), employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getEmail());
    }
}
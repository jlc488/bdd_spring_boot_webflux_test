package iam.sinny.springbootwebfluxtesting;

import iam.sinny.springbootwebfluxtesting.controller.EmployeeController;
import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import iam.sinny.springbootwebfluxtesting.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    EmployeeService employeeService;

    //JUnit Test for Save Employee
    @Test
    @DisplayName("Save Employee Test")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        given(employeeService.saveEmployee(any(employeeDTO.getClass()))).willReturn(just(employeeDTO));

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(just(employeeDTO), employeeDTO.getClass())
                .exchange();


        //then - verify the output
        response.expectStatus().isCreated()
                .expectBody(EmployeeDTO.class)
                .consumeWith(System.out::println);
    }

    // JUnit Test for Get Employee By Id
    @Test
    @DisplayName("Get Employee By Id Test")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        given(employeeService.getEmployeeById(anyString())).willReturn(just(employeeDTO));

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .consumeWith(System.out::println);

    }

    // JUnit Test for Get All Employees
    @Test
    @DisplayName("Get All Employees Test")
    public void givenEmployeeId_whenGetAllEmployees_thenReturnEmployeeObject() {
        //given - precondition or setup
        List<EmployeeDTO> list = new ArrayList<>();

        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setFirstName("Sinny1");
        employeeDTO1.setLastName("Kang1");
        employeeDTO1.setEmail("jlc1@gmail.com");

        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setFirstName("Sinny2");
        employeeDTO2.setLastName("Kang2");
        employeeDTO2.setEmail("jlc2@gmail.com");

        list.add(employeeDTO1);
        list.add(employeeDTO2);

        given(employeeService.getAllEmployees()).willReturn(Flux.fromIterable(list));

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBodyList(EmployeeDTO.class)
                .consumeWith(System.out::println);
    }

    // JUnit Test for Update Employee
    @Test
    @DisplayName("Update Employee Test")
    public void givenEmployeeIdAndEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        //given - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        given(employeeService.updateEmployee(anyString(), any(employeeDTO.getClass()))).willReturn(just(employeeDTO));

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/employees/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(just(employeeDTO), employeeDTO.getClass())
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .consumeWith(System.out::println);
    }

    // JUnit Test for Delete Employee
    @Test
    @DisplayName("Delete Employee Test")
    public void givenEmployeeId_whenDeleteEmployee_thenEmployeeObjectShouldBeDeleted() {
        //given - precondition or setup
        given(employeeService.deleteEmployeeById(anyString())).willReturn(empty());

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.delete().uri("/api/employees/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isNoContent()
                .expectBody(Void.class)
                .consumeWith(System.out::println);
    }
}
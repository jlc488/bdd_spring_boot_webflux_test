package iam.sinny.springbootwebfluxtesting;

import iam.sinny.springbootwebfluxtesting.dto.EmployeeDTO;
import iam.sinny.springbootwebfluxtesting.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        employeeService.deleteAllEmployees().block();
    }

    //JUnit Test for Save Employee
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeDTO)
                .exchange();

        //then - verify the output
        response.expectStatus().isCreated()
                .expectBody(EmployeeDTO.class)
                .consumeWith(System.out::println);

    }

    //JUnit Test for Get Employee By Id
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees/{id}", savedEmployeeDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .consumeWith(System.out::println);
    }

    //JUnit Test for Get All Employees
    @Test
    public void givenEmployeeId_whenGetAllEmployees_thenReturnEmployeeObject() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBodyList(EmployeeDTO.class)
                .hasSize(1)
                .consumeWith(System.out::println);
    }

    //JUnit Test for Update Employee
    @Test
    public void givenEmployeeIdAndEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setFirstName("Sinny2");
        updatedEmployeeDTO.setLastName("Kang2");
        updatedEmployeeDTO.setEmail("jlc@gmail.com");

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/employees/{id}", savedEmployeeDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatedEmployeeDTO)
                .exchange();

        //then - verify the output
        response.expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("jlc@gmail.com")
                .jsonPath("$.firstName").isEqualTo("Sinny2")
                .jsonPath("$.lastName").isEqualTo("Kang2")
                .consumeWith(System.out::println);
    }

    //JUnit Test for Delete Employee By Id
    @Test
    public void givenEmployeeId_whenDeleteEmployeeById_thenEmployeeShouldBeDeleted() {
        //give - precondition or setup
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Sinny");
        employeeDTO.setLastName("Kang");
        employeeDTO.setEmail("jlc488@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();

        //when - action or the behaviour that we are test
        WebTestClient.ResponseSpec response = webTestClient.delete().uri("/api/employees/{id}", savedEmployeeDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then - verify the output
        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
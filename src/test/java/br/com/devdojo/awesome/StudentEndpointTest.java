package br.com.devdojo.awesome;

import br.com.devdojo.awesome.Repository.StudentRepository;
import br.com.devdojo.awesome.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .basicAuthentication("carol", "thiago27");
        }
    }

    @Test
    public void whenListStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
        System.out.println(port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);

    }

    @Test
    public void whenGetStudentByIdUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/1", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenFindStudentByNameUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/findByName/studentName", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenListStudentUsingCorrectUsernameAndPassword_thenReturnStatusCode200() {
        List<Student> students = asList(
                new Student(1L, "THIAGO", "THIAGO@GMAIL.COM"),
                new Student(2L, "THIAGO2", "THIAGO2@GMAIL.COM"));


        BDDMockito.when(studentRepository.findAll()).thenReturn(students);

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void whenGetStudentByIdUsingCorrectUsernameAndPasswordAndStudentDoesNotExists_thenReturnStatusCode404() {
        Student student=  new Student(1L, "THIAGO", "THIAGO@GMAIL.COM");

        BDDMockito.when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));

        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/{id}", Student.class, 2);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    public void whenGetStudentByIdUsingCorrectUsernameAndPassword_thenReturnStatusCode200() {
        Student student=  new Student(1L, "THIAGO", "THIAGO@GMAIL.COM");

        BDDMockito.when(studentRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));

        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/{id}", Student.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void whenSaveStudentUsingIncorrectUsernameAndPassword_thenReturnResourceAccessException () {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        Student student = new Student(1L, "Legolas", "legolas@lotr.com");

        assertThrows(
                ResourceAccessException.class,
                () -> restTemplate.postForEntity("http://localhost:8080/v1/admin/students/", student, String.class));
    }



}

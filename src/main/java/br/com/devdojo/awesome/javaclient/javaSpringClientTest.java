package br.com.devdojo.awesome.javaclient;

import br.com.devdojo.awesome.model.PageableResponse;
import br.com.devdojo.awesome.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class javaSpringClientTest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/students")
                .basicAuthentication("carol", "thiago27")
                .build();
        Student student = restTemplate.getForObject("/{id}", Student.class, 15);
        System.out.println(student);

        //listando student por id response entity
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 15);
        System.out.println(forEntity);
        System.out.println(forEntity.getBody());
        // Listando todos os students um array
//        Student[] students = restTemplate.getForObject("/", Student[].class );
//        System.out.println(Arrays.toString(students));
//
//        //
//        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Student>>() {
//                });
//        System.out.println(exchange.getBody());

        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET, null,
       new ParameterizedTypeReference<PageableResponse<Student>>() {});
        System.out.println(exchange);
    }
}

package br.com.devdojo.awesome.javaclient;

import br.com.devdojo.awesome.handler.RestResponseExecpetionHandler;
import br.com.devdojo.awesome.model.PageableResponse;
import br.com.devdojo.awesome.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {
    RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/students")
            .basicAuthentication("carol", "thiago27")
            .errorHandler(new RestResponseExecpetionHandler())
            .build();

    RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthentication("carol2","thiago27")
            .errorHandler(new RestResponseExecpetionHandler())
            .build();

    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
        //listando student por id response entity
        //ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 15);
        //System.out.println(forEntity.getBody());
    }

    public List<Student> listAll () {
        ResponseEntity<PageableResponse<Student>> exchange= restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {});
        return  exchange.getBody().getContent();
    }

    public Student save (Student student) {
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/",
                HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()), Student.class);
        return  exchangePost.getBody();

        // FOR OBJECT
        //Student studentPostForObject = restTemplateAdmin.postForObject("/", studentPost,  Student.class);
        //System.out.println(studentPostForObject);

        // ENTITY POST
        //ResponseEntity<Student> studentResponseEntity = restTemplateAdmin.postForEntity("/", studentPost, Student.class);
        //System.out.println(studentResponseEntity);
    }



    public void update (Student student) {
        restTemplateAdmin.put("/", student);
    }

    public void delete (int id) {

        restTemplateAdmin.delete("/{id}", id);
    }
    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}

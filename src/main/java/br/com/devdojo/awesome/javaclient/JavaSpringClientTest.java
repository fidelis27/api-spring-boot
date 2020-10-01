package br.com.devdojo.awesome.javaclient;

import br.com.devdojo.awesome.model.Student;

public class JavaSpringClientTest {

    public static void main(String[] args) {


        // Listando todos os students um array
        //Student[] students = restTemplate.getForObject("/", Student[].class );
        //System.out.println(Arrays.toString(students));
        JavaClientDAO dao = new JavaClientDAO();


        //metodo POST
        Student studentPost = new Student();
        studentPost.setName("rfa fidelis");
        studentPost.setEmail("thiago@gmail.com");
        //System.out.println(dao.findById(60));
        //System.out.println(dao.listAll());
        System.out.println(dao.save(studentPost));









    }


}

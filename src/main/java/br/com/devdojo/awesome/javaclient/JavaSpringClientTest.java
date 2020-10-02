package br.com.devdojo.awesome.javaclient;

import br.com.devdojo.awesome.model.Student;

public class JavaSpringClientTest {

    public static void main(String[] args) {


        // Listando todos os students um array
        //Student[] students = restTemplate.getForObject("/", Student[].class );
        //System.out.println(Arrays.toString(students));
        JavaClientDAO dao = new JavaClientDAO();


        //metodo POST
        Student student = new Student("","thiago@gmail.com");
//        student.setName();
//        student.setEmail();
        student.setId(68L);
        //System.out.println(dao.findById(61));
        //System.out.println(dao.listAll());
        System.out.println(dao.save(student));
        //dao.update(student);
        //dao.delete(61);









    }


}

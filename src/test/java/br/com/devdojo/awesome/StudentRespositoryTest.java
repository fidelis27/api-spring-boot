package br.com.devdojo.awesome;

import br.com.devdojo.awesome.Repository.StudentRepository;
import br.com.devdojo.awesome.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // <- ativa testes usando o próprio bd
public class StudentRespositoryTest {
    @Autowired
    private StudentRepository studentRepository;


    @Test
    public void whenCreate_thenPersistData() {

        Student student = new Student("Thiago", "thiago.teste@email.com");
        this.studentRepository.save(student);


        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Thiago");
        assertThat(student.getEmail()).isEqualTo("thiago.teste@email.com");

    }

    @Test
    public void whenDelete_thenRemoveData() {
        Student student = new Student("Thiago", "thiago.teste@email.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findById(student.getId())).isEmpty();

    }

    @Test
    public void whenUpdate_thenChangeAndPersistData() {
        Student student = new Student("Thiago", "thiago.teste@email.com");
        this.studentRepository.save(student);

        student = new Student("Thiago Dois", "thiago.testedois@email.com");
        this.studentRepository.save(student);

        student = this.studentRepository.findById(student.getId()).orElse(null);

        assertThat(student.getName()).isEqualTo("Thiago Dois");
        assertThat(student.getEmail()).isEqualTo("thiago.testedois@email.com");
    }

    @Test
    public void whenFindByNameIgnoreCaseContaining_thenIgnoreCase() {
        Student student1 = new Student("Thiago", "thiago.teste@email.com");
        Student student2 = new Student("thiago", "thiago.teste2@email.com");

        this.studentRepository.save(student1);
        this.studentRepository.save(student2);

        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("thiago");

        assertThat(studentList.size()).isEqualTo(48);

    }

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void whenNotEmptyName_thenNoConstraintViolations() {
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    studentRepository.save(new Student("", "email@gmail.com"));
                    entityManager.flush();
                }
        );
        assertTrue(exception.getMessage().contains("O campo nome do estudante é obrigatório!"));
    }
    @Test
    public void whenNotEmptyEmail_thenNoConstraintViolations () {
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    studentRepository.save(new Student("Thiago", ""));
                    entityManager.flush();
                });

        assertTrue(exception.getMessage().contains("O campo email é obrigatório"));
    }
    @Test
    public void whenValidEmail_thenNoConstraintViolations () {
        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    studentRepository.save(new Student("Thiago", "thiago.fidelis.com"));
                    entityManager.flush();
                });

        assertTrue(exception.getMessage().contains("O email deve ser válido"));
    }

}

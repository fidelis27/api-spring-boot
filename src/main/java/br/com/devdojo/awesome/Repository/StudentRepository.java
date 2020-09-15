package br.com.devdojo.awesome.Repository;

import br.com.devdojo.awesome.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);

}

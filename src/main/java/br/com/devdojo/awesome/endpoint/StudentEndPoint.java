package br.com.devdojo.awesome.endpoint;


import br.com.devdojo.awesome.Repository.StudentRepository;
import br.com.devdojo.awesome.error.ResourceNotFoundException;
import br.com.devdojo.awesome.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("v1")
public class StudentEndPoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndPoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(path = "students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        //System.out.println("data:"+dateUtil.formatLocalDataTimeToDatabaseStyle(LocalDateTime.now()));

        return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.GET, path ="/{id}")
    @GetMapping(path = "students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);

        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "students/findByName/{name}")
    public ResponseEntity<?> FindStudentByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping(path = "admin/students")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    //@RequestMapping(method = RequestMethod.DELETE)

    @DeleteMapping(path = "admin/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.PUT)
    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {
        if (!studentDAO.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID:" + id);
        }
    }

}
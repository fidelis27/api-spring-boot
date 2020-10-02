package br.com.devdojo.awesome.model;


import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class Student extends AbstractEntity {
    @NotEmpty(message = "O campo nome do estudante é obrigatório!")
    private String name;
    @NotEmpty(message = "O campo email é obrigatório!")
    @Email(message = "O email deve ser válido")
    private String email;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//    public Student () {
//    }

    public Student(@NotEmpty(message = "O campo nome do estudante é obrigatório!") String name, @NotEmpty(message = "O campo email é obrigatório!") @Email(message = "O email deve ser válido")String email) {
        this.name = name;
        this.email = email;
    }
}

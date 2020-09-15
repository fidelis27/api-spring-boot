package br.com.devdojo.awesome.Repository;

import br.com.devdojo.awesome.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface  UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername (String username);
}

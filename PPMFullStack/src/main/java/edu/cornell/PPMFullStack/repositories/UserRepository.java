package edu.cornell.PPMFullStack.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.cornell.PPMFullStack.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User getById(Long id);
}

package edu.cornell.PPMFullStack.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.cornell.PPMFullStack.domain.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String projectIdentifier);

}

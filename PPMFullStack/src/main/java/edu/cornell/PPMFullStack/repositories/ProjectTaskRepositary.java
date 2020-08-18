package edu.cornell.PPMFullStack.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.cornell.PPMFullStack.domain.ProjectTask;

public interface ProjectTaskRepositary extends CrudRepository<ProjectTask, Long> {

}

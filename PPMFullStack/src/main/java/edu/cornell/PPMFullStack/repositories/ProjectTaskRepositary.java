package edu.cornell.PPMFullStack.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.cornell.PPMFullStack.domain.ProjectTask;

public interface ProjectTaskRepositary extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    ProjectTask findByProjectSequence(String sequence);
}

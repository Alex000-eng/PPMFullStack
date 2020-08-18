package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.domain.Backlog;
import edu.cornell.PPMFullStack.domain.ProjectTask;
import edu.cornell.PPMFullStack.repositories.BacklogRepository;
import edu.cornell.PPMFullStack.repositories.ProjectTaskRepositary;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepositary projectTaskRepositary;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        // need to take care the situation if the project doesnt exist

        Backlog backlog= backlogRepository.findByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        Integer BacklogSequence= backlog.getPTSequence();

        BacklogSequence++ ;

        backlog.setPTSequence(BacklogSequence);

        // then add the sequence to the project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);

        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getPriority() == null)
            projectTask.setPriority(3);

        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepositary.save(projectTask);

    }

}

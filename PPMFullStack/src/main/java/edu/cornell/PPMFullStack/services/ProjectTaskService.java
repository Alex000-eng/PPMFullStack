package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.ProjectNotFoundException;
import edu.cornell.PPMFullStack.domain.Backlog;
import edu.cornell.PPMFullStack.domain.ProjectTask;
import edu.cornell.PPMFullStack.repositories.BacklogRepository;
import edu.cornell.PPMFullStack.repositories.ProjectRepository;
import edu.cornell.PPMFullStack.repositories.ProjectTaskRepositary;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepositary projectTaskRepositary;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,
        String username) {
        // need to take care the situation if the project doesnt exist

        Backlog backlog= projectService
            .findProjectByProjectIdentifer(projectIdentifier, username).getBacklog();

        // Backlog backlog= backlogRepository.findByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        Integer BacklogSequence= backlog.getPTSequence();

        BacklogSequence++ ;

        backlog.setPTSequence(BacklogSequence);

        // then add the sequence to the project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);

        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
            projectTask.setPriority(3);

        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepositary.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
        // TODO Auto-generated method stub

        projectService.findProjectByProjectIdentifer(backlog_id, username);

        return projectTaskRepositary.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String projectSequence,
        String username) {

        projectService.findProjectByProjectIdentifer(backlog_id, username);

        ProjectTask projectTask= projectTaskRepositary.findByProjectSequence(projectSequence);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task: " + projectSequence + " not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException(
                "Project Task " + projectSequence + " doesn't exist in project " + backlog_id);
        }

        return projectTaskRepositary.findByProjectSequence(projectSequence);
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id,
        String projectSequence, String username) {
        ProjectTask projectTask= findProjectTaskByProjectSequence(backlog_id, projectSequence,
            username);

        projectTask= updatedTask;

        return projectTaskRepositary.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String projectSequence,
        String username) {
        ProjectTask projectTask= findProjectTaskByProjectSequence(backlog_id, projectSequence,
            username);

        projectTaskRepositary.delete(projectTask);

    }

}

package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.ProjectNotFoundException;
import edu.cornell.PPMFullStack.domain.Backlog;
import edu.cornell.PPMFullStack.domain.Project;
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

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        // need to take care the situation if the project doesnt exist

        try {

            Backlog backlog= backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer BacklogSequence= backlog.getPTSequence();

            BacklogSequence++ ;

            backlog.setPTSequence(BacklogSequence);

            // then add the sequence to the project task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);

            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null)
                projectTask.setPriority(3);

            if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepositary.save(projectTask);

        } catch (Exception e) {
            throw new ProjectNotFoundException("Project Not Found");
        }

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        // TODO Auto-generated method stub
        Project project= projectRepository.findByProjectIdentifier(backlog_id);

        if (project == null) { throw new ProjectNotFoundException("Project not found!"); }
        return projectTaskRepositary.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String projectSequence) {

        Backlog backlog= backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project with id: " + backlog_id + " doesn't exist");
        }

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
        String projectSequence) {
        ProjectTask projectTask= findProjectTaskByProjectSequence(backlog_id, projectSequence);

        projectTask= updatedTask;

        return projectTaskRepositary.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String projectSequence) {
        ProjectTask projectTask= findProjectTaskByProjectSequence(backlog_id, projectSequence);

        projectTaskRepositary.delete(projectTask);

    }

}

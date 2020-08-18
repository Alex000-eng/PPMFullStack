package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.ProjectIdException;
import edu.cornell.PPMFullStack.domain.Backlog;
import edu.cornell.PPMFullStack.domain.Project;
import edu.cornell.PPMFullStack.repositories.BacklogRepository;
import edu.cornell.PPMFullStack.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {

        // Logic
        try {
            String upper= project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(upper);

            if (project.getId() == null) {
                Backlog backlog= new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(upper);
            }
            if (project.getId() != null) {
                project.setBacklog(backlogRepository
                    .findByProjectIdentifier(upper));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException(
                "Project Id " + project.getProjectIdentifier() + " already existed");
        }

    }

    public Project findProjectByProjectIdentifer(String projectIdentifier) {

        Project project= projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException(
                "Project Id" + projectIdentifier + "doesn't exist");
        }

        return project;
    }

    public Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId) {
        Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project with id " + projectId + " doesn't exist");
        }

        projectRepository.delete(project);

    }

    @Deprecated
    public Project updateProjectById(String projectId, Project newProject) {
        Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project with id " + projectId + " doesn't exist");
        }

        projectRepository.delete(project);

        return saveOrUpdateProject(newProject);

    }

}

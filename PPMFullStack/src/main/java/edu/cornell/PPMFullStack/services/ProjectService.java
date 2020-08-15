package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.ProjectIdException;
import edu.cornell.PPMFullStack.domain.Project;
import edu.cornell.PPMFullStack.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        // Logic
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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

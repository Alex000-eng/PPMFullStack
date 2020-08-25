package edu.cornell.PPMFullStack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cornell.PPMFullStack.Exceptions.ProjectIdException;
import edu.cornell.PPMFullStack.Exceptions.ProjectNotFoundException;
import edu.cornell.PPMFullStack.domain.Backlog;
import edu.cornell.PPMFullStack.domain.Project;
import edu.cornell.PPMFullStack.domain.User;
import edu.cornell.PPMFullStack.repositories.BacklogRepository;
import edu.cornell.PPMFullStack.repositories.ProjectRepository;
import edu.cornell.PPMFullStack.repositories.UserRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {

        // find by db is
        if (project.getId() != null) {
            // try to update
            Project existingProject= projectRepository
                .findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null &&
                !existingProject.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID : " +
                    project.getProjectIdentifier() + " can't be updated because it doesn't exist!");
            }

        }

        // Logic
        try {

            User user= userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());

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

    public Project findProjectByProjectIdentifer(String projectIdentifier, String username) {

        Project project= projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException(
                "Project Id" + projectIdentifier + " doesn't exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException(
                "project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProject(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectById(String projectId, String username) {
//        Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//
//        if (project == null) {
//            throw new ProjectIdException("Project with id " + projectId + " doesn't exist");
//        }

        Project project= findProjectByProjectIdentifer(projectId, username);
        projectRepository.delete(project);

    }

}

package edu.cornell.PPMFullStack.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cornell.PPMFullStack.domain.Project;
import edu.cornell.PPMFullStack.services.MapValidationErrorService;
import edu.cornell.PPMFullStack.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
        BindingResult result, Principal principal) {

        ResponseEntity<?> errorMapEntity= mapValidationErrorService.MapValidationServce(result);
        if (errorMapEntity != null) return errorMapEntity;

        Project newProject= projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
        Project project= projectService.findProjectByProjectIdentifer(projectId,
            principal.getName());

        return new ResponseEntity<>(project, HttpStatus.OK);

    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProject(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId,
        Principal principal) {

        projectService.deleteProjectById(projectId, principal.getName());

        return new ResponseEntity<>(
            "Project with id " + projectId + " was deleted successfully", HttpStatus.OK);

    }

}

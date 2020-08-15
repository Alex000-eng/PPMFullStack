package edu.cornell.PPMFullStack.web;

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
        BindingResult result) {

        ResponseEntity<?> errorMapEntity= mapValidationErrorService.MapValidationServce(result);
        if (errorMapEntity != null) return errorMapEntity;

        Project newProject= projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project= projectService.findProjectByProjectIdentifer(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);

    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProject();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId) {

        projectService.deleteProjectById(projectId);

        return new ResponseEntity<>(
            "Project with id " + projectId + " was deleted successfully", HttpStatus.OK);

    }

    @Deprecated
    @PostMapping("/{projectId}")
    public ResponseEntity<?> updateProjectById(@PathVariable String projectId,
        @Valid @RequestBody Project newProject, BindingResult result) {

        ResponseEntity<?> errorMapEntity= mapValidationErrorService.MapValidationServce(result);
        if (errorMapEntity != null) return errorMapEntity;

        Project project= projectService.updateProjectById(projectId, newProject);

        return new ResponseEntity<>(newProject, HttpStatus.OK);

    }

}

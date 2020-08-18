package edu.cornell.PPMFullStack.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cornell.PPMFullStack.domain.ProjectTask;
import edu.cornell.PPMFullStack.services.MapValidationErrorService;
import edu.cornell.PPMFullStack.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
        BindingResult result, @PathVariable String backlog_id) {

        ResponseEntity<?> errorMap= mapValidationErrorService.MapValidationServce(result);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask1= projectTaskService.addProjectTask(backlog_id, projectTask);

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }
}
